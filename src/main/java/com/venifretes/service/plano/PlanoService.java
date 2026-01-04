package com.venifretes.service.plano;

import com.venifretes.dto.request.PlanoRequest;
import com.venifretes.dto.response.PlanoResponse;
import com.venifretes.model.entity.Plano;
import com.venifretes.model.enums.PlanoTipo;
import com.venifretes.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoService {

    private final PlanoRepository planoRepository;

    /**
     * Lista todos os planos ativos
     */
    @Transactional(readOnly = true)
    public List<PlanoResponse> listarPlanosAtivos() {
        log.debug("Listando planos ativos");
        return planoRepository.findAllByAtivoTrueOrderByOrdem()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os planos (incluindo inativos) - apenas para admin
     */
    @Transactional(readOnly = true)
    public List<PlanoResponse> listarTodosPlanos() {
        log.debug("Listando todos os planos");
        return planoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um plano por tipo
     */
    @Transactional(readOnly = true)
    public PlanoResponse buscarPorTipo(PlanoTipo tipo) {
        log.debug("Buscando plano por tipo: {}", tipo);
        Plano plano = planoRepository.findById(tipo)
                .orElseThrow(() -> {
                    log.error("Plano não encontrado: {}", tipo);
                    return new RuntimeException("Plano não encontrado: " + tipo);
                });
        return toResponse(plano);
    }

    /**
     * Cria um novo plano
     */
    @Transactional
    public PlanoResponse criarPlano(PlanoTipo tipo, PlanoRequest request) {
        log.info("Criando novo plano: {}", tipo);

        if (planoRepository.existsById(tipo)) {
            log.error("Plano já existe: {}", tipo);
            throw new RuntimeException("Já existe um plano com o tipo: " + tipo);
        }

        Plano plano = Plano.builder()
                .id(tipo)
                .nome(request.getNome())
                .preco(request.getPreco())
                .features(request.getFeatures())
                .posicaoRanking(request.getPosicaoRanking())
                .limiteFotos(request.getLimiteFotos())
                .ordem(request.getOrdem())
                .ativo(request.getAtivo() != null ? request.getAtivo() : true)
                .build();

        plano = planoRepository.save(plano);
        log.info("Plano criado com sucesso: {}", tipo);

        return toResponse(plano);
    }

    /**
     * Atualiza um plano existente
     */
    @Transactional
    public PlanoResponse atualizarPlano(PlanoTipo tipo, PlanoRequest request) {
        log.info("Atualizando plano: {}", tipo);

        Plano plano = planoRepository.findById(tipo)
                .orElseThrow(() -> {
                    log.error("Plano não encontrado para atualização: {}", tipo);
                    return new RuntimeException("Plano não encontrado: " + tipo);
                });

        plano.setNome(request.getNome());
        plano.setPreco(request.getPreco());
        plano.setFeatures(request.getFeatures());
        plano.setPosicaoRanking(request.getPosicaoRanking());
        plano.setLimiteFotos(request.getLimiteFotos());
        plano.setOrdem(request.getOrdem());

        if (request.getAtivo() != null) {
            plano.setAtivo(request.getAtivo());
        }

        plano = planoRepository.save(plano);
        log.info("Plano atualizado com sucesso: {}", tipo);

        return toResponse(plano);
    }

    /**
     * Ativa ou desativa um plano
     */
    @Transactional
    public PlanoResponse alterarStatusPlano(PlanoTipo tipo, boolean ativo) {
        log.info("Alterando status do plano {}: ativo={}", tipo, ativo);

        Plano plano = planoRepository.findById(tipo)
                .orElseThrow(() -> {
                    log.error("Plano não encontrado: {}", tipo);
                    return new RuntimeException("Plano não encontrado: " + tipo);
                });

        plano.setAtivo(ativo);
        plano = planoRepository.save(plano);

        log.info("Status do plano {} alterado com sucesso para: {}", tipo, ativo);
        return toResponse(plano);
    }

    /**
     * Deleta um plano (soft delete - apenas desativa)
     */
    @Transactional
    public void deletarPlano(PlanoTipo tipo) {
        log.info("Deletando (desativando) plano: {}", tipo);
        alterarStatusPlano(tipo, false);
    }

    /**
     * Converte entidade para DTO de resposta
     */
    private PlanoResponse toResponse(Plano plano) {
        return PlanoResponse.builder()
                .id(plano.getId())
                .nome(plano.getNome())
                .preco(plano.getPreco())
                .features(plano.getFeatures())
                .posicaoRanking(plano.getPosicaoRanking())
                .limiteFotos(plano.getLimiteFotos())
                .ordem(plano.getOrdem())
                .build();
    }
}
