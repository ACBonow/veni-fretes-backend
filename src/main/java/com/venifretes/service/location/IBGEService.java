package com.venifretes.service.location;

import com.venifretes.model.entity.Cidade;
import com.venifretes.model.entity.Estado;
import com.venifretes.repository.CidadeRepository;
import com.venifretes.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IBGEService {

    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final RestTemplate restTemplate;

    private static final String IBGE_API_BASE = "https://servicodados.ibge.gov.br/api/v1";

    /**
     * Import cities from IBGE API for a specific state
     * API endpoint: /localidades/estados/{UF}/municipios
     *
     * @param estadoSigla State abbreviation (e.g., "RS")
     */
    @Transactional
    public void importCidadesByEstado(String estadoSigla) {
        log.info("Starting city import for state: {}", estadoSigla);

        // Find or validate estado
        Estado estado = estadoRepository.findBySigla(estadoSigla)
            .orElseThrow(() -> new IllegalArgumentException("Estado not found: " + estadoSigla));

        // Check if already imported
        long existing = cidadeRepository.countByEstado(estado);
        if (existing > 0) {
            log.warn("State {} already has {} cities. Skipping import.", estadoSigla, existing);
            return;
        }

        // Call IBGE API
        String url = String.format("%s/localidades/estados/%s/municipios", IBGE_API_BASE, estadoSigla);

        try {
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response == null || response.isEmpty()) {
                log.error("No cities returned from IBGE API for state: {}", estadoSigla);
                return;
            }

            int imported = 0;
            for (Map<String, Object> municipio : response) {
                Integer codigoIbge = (Integer) municipio.get("id");
                String nome = (String) municipio.get("nome");

                // Check if already exists
                if (cidadeRepository.findByCodigoIbge(codigoIbge).isPresent()) {
                    continue;
                }

                Cidade cidade = Cidade.builder()
                    .estado(estado)
                    .nome(nome)
                    .codigoIbge(codigoIbge)
                    .build();

                cidadeRepository.save(cidade);
                imported++;
            }

            log.info("Successfully imported {} cities for state {}", imported, estadoSigla);

        } catch (Exception e) {
            log.error("Error importing cities from IBGE for state {}: {}", estadoSigla, e.getMessage());
            throw new RuntimeException("Failed to import cities from IBGE", e);
        }
    }

    /**
     * One-time import for Rio Grande do Sul
     */
    @Transactional
    public void importRioGrandeDoSul() {
        log.info("Importing Rio Grande do Sul cities from IBGE...");
        importCidadesByEstado("RS");
    }
}
