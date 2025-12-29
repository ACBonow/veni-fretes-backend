package com.venifretes.service.migration;

import com.venifretes.model.entity.*;
import com.venifretes.model.enums.*;
import com.venifretes.repository.*;
import com.venifretes.util.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreteiroMigrationService {

    private final FreteiroRepository freteiroRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * DTO to hold old database row data
     */
    public static class OldFreteiroData {
        public String nome;
        public String email;
        public String telefone;
        public String senha; // Already hashed from old system
        public String veiculo; // "Camionete, Caminhão"
        public String dias; // "Segunda a Sexta, Sábado, Domingo, Feriados"
        public String modelo; // "Municipal, Intermunicipal, Interestadual"
        public String servicos; // "Empacotamento" or "Transporte de Animais" or empty
        public String mudanca; // "Sim" or "Não"
        public String estado; // "RS" or null
        public String cidade; // "Pelotas" or "Porto Alegre, Canoas, Pelotas" or null
        public String fotoPerfil;
        public Integer pontos; // Will be imported separately to pontos_ranking
    }

    @Transactional
    public Freteiro migrateFreteiro(OldFreteiroData old) {
        log.info("Migrating freteiro: {}", old.nome);

        try {
            // 1. Parse vehicle types
            List<TipoVeiculo> tiposVeiculo = parseTiposVeiculo(old.veiculo);

            // 2. Parse service types
            List<TipoServico> tiposServico = parseTiposServico(old.modelo, old.servicos, old.mudanca);

            // 3. Resolve cidade
            Cidade cidade = resolveCidade(old.estado, old.cidade);

            // 4. Parse working days (for default schedule)
            List<DiaDaSemana> diasTrabalho = DiaDaSemana.parseFromOldFormat(old.dias);

            // 5. Generate unique slug
            String slug = gerarSlugUnico(old.nome);

            // 6. Handle password - keep old hash or generate default
            String password = (old.senha != null && !old.senha.isEmpty())
                ? old.senha
                : passwordEncoder.encode("senha123"); // Default for null passwords

            // 7. Handle email - generate if missing
            String email = (old.email != null && !old.email.isEmpty())
                ? old.email
                : slug + "@venifretes.temp";

            // 8. Build Freteiro entity
            Freteiro freteiro = Freteiro.builder()
                .nome(old.nome)
                .email(email)
                .telefone(normalizeTelefone(old.telefone))
                .password(password)
                .role(Role.FRETEIRO)
                .emailVerificado(false)
                .ativo(true)
                .slug(slug)
                .cidadeRef(cidade)
                // Keep old fields for rollback safety
                .cidade(cidade != null ? cidade.getNome() : old.cidade)
                .estado(old.estado)
                .fotoPerfil(old.fotoPerfil)
                .tiposVeiculo(tiposVeiculo)
                .tiposServico(tiposServico)
                .build();

            // 9. Create default working hours (all working days, 8:00-18:00)
            List<HorarioAtendimento> horarios = createDefaultHorarios(freteiro, diasTrabalho);
            freteiro.setHorariosAtendimento(horarios);

            // 10. Parse areas atendidas if multiple cities
            List<String> areasAtendidas = parseAreasAtendidas(old.cidade);
            freteiro.setAreasAtendidas(areasAtendidas);

            // 11. Save
            Freteiro saved = freteiroRepository.save(freteiro);

            log.info("Successfully migrated freteiro: {} (ID: {})", saved.getNome(), saved.getId());
            return saved;

        } catch (Exception e) {
            log.error("Error migrating freteiro {}: {}", old.nome, e.getMessage(), e);
            throw new RuntimeException("Migration failed for: " + old.nome, e);
        }
    }

    private List<TipoVeiculo> parseTiposVeiculo(String veiculo) {
        if (veiculo == null || veiculo.trim().isEmpty()) {
            return List.of(TipoVeiculo.CAMINHONETE); // Default
        }

        Map<String, TipoVeiculo> mapping = Map.of(
            "camionete", TipoVeiculo.CAMINHONETE,
            "caminhonete", TipoVeiculo.CAMINHONETE,
            "caminhão", TipoVeiculo.CAMINHAO_TOCO,
            "caminhao", TipoVeiculo.CAMINHAO_TOCO,
            "carro", TipoVeiculo.CARRO,
            "van", TipoVeiculo.VAN
        );

        return Arrays.stream(veiculo.split(","))
            .map(String::trim)
            .map(String::toLowerCase)
            .map(v -> mapping.getOrDefault(v, TipoVeiculo.CAMINHONETE))
            .distinct()
            .collect(Collectors.toList());
    }

    private List<TipoServico> parseTiposServico(String modelo, String servicos, String mudanca) {
        List<TipoServico> result = new ArrayList<>();

        // Parse "modelo" field
        if (modelo != null && !modelo.isEmpty()) {
            if (modelo.contains("Municipal")) {
                result.add(TipoServico.FRETES_LOCAIS);
            }
            if (modelo.contains("Intermunicipal")) {
                result.add(TipoServico.FRETES_ESTADUAIS);
            }
            if (modelo.contains("Interestadual")) {
                result.add(TipoServico.FRETES_INTERESTADUAIS);
            }
        }

        // Parse "servicos" field
        if (servicos != null && !servicos.isEmpty()) {
            if (servicos.toLowerCase().contains("empacotamento")) {
                result.add(TipoServico.EMPACOTAMENTO);
            }
            if (servicos.toLowerCase().contains("transporte de animais")) {
                result.add(TipoServico.TRANSPORTE_ANIMAIS);
            }
        }

        // Parse "mudanca" field
        if ("Sim".equalsIgnoreCase(mudanca)) {
            result.add(TipoServico.MUDANCA);
        }

        // Default if empty
        if (result.isEmpty()) {
            result.add(TipoServico.CARRETO);
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

    private Cidade resolveCidade(String estadoSigla, String cidadeNome) {
        // Use defaults if missing
        final String finalEstadoSigla = (estadoSigla == null || estadoSigla.trim().isEmpty()) ? "RS" : estadoSigla;
        final String finalCidadeNome = (cidadeNome == null || cidadeNome.trim().isEmpty()) ? "Pelotas" : cidadeNome;

        if (!finalEstadoSigla.equals(estadoSigla) || !finalCidadeNome.equals(cidadeNome)) {
            log.warn("Missing estado/cidade, using default (Pelotas/RS)");
        }

        // If multiple cities, use first one
        final String primaryCidade = finalCidadeNome.split(",")[0].trim();

        return cidadeRepository.findByEstadoSiglaAndNomeIgnoreCase(finalEstadoSigla, primaryCidade)
            .orElseGet(() -> {
                log.warn("Cidade not found: {} - {}, using Pelotas", finalEstadoSigla, primaryCidade);
                return cidadeRepository.findByEstadoSiglaAndNomeIgnoreCase("RS", "Pelotas")
                    .orElseThrow(() -> new RuntimeException("Default city Pelotas not found"));
            });
    }

    private List<String> parseAreasAtendidas(String cidade) {
        if (cidade == null || !cidade.contains(",")) {
            return new ArrayList<>();
        }

        return Arrays.stream(cidade.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .skip(1) // Skip first (primary city)
            .collect(Collectors.toList());
    }

    private List<HorarioAtendimento> createDefaultHorarios(Freteiro freteiro, List<DiaDaSemana> dias) {
        List<HorarioAtendimento> horarios = new ArrayList<>();

        for (DiaDaSemana dia : dias) {
            // Create default schedule: 8:00-12:00 and 14:00-18:00
            horarios.add(HorarioAtendimento.builder()
                .freteiro(freteiro)
                .diaSemana(dia)
                .horaInicio("08:00")
                .horaFim("12:00")
                .build());

            horarios.add(HorarioAtendimento.builder()
                .freteiro(freteiro)
                .diaSemana(dia)
                .horaInicio("14:00")
                .horaFim("18:00")
                .build());
        }

        return horarios;
    }

    private String normalizeTelefone(String telefone) {
        if (telefone == null) {
            return "5300000000"; // Placeholder
        }
        // Remove non-digits
        return telefone.replaceAll("[^0-9]", "");
    }

    private String gerarSlugUnico(String nome) {
        String baseSlug = SlugGenerator.generate(nome);
        String slug = baseSlug;
        int counter = 1;

        while (freteiroRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }
}
