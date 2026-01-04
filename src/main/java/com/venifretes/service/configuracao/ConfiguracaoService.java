package com.venifretes.service.configuracao;

import com.venifretes.dto.request.ConfiguracaoRequest;
import com.venifretes.dto.response.ConfiguracaoResponse;
import com.venifretes.model.entity.ConfiguracaoSistema;
import com.venifretes.model.enums.TipoConfiguracao;
import com.venifretes.repository.ConfiguracaoSistemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguracaoService {

    private final ConfiguracaoSistemaRepository configuracaoRepository;

    /**
     * Lista todas as configurações do sistema
     */
    @Transactional(readOnly = true)
    public List<ConfiguracaoResponse> listarTodasConfiguracoes() {
        log.debug("Listando todas as configurações do sistema");
        return configuracaoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma configuração específica
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "configuracoes", key = "#chave")
    public ConfiguracaoResponse buscarConfiguracao(TipoConfiguracao chave) {
        log.debug("Buscando configuração: {}", chave);
        ConfiguracaoSistema config = configuracaoRepository.findById(chave)
                .orElseGet(() -> criarConfiguracaoPadrao(chave));
        return toResponse(config);
    }

    /**
     * Atualiza uma configuração
     */
    @Transactional
    @CacheEvict(value = "configuracoes", key = "#chave")
    public ConfiguracaoResponse atualizarConfiguracao(TipoConfiguracao chave, ConfiguracaoRequest request) {
        log.info("Atualizando configuração: {}", chave);

        ConfiguracaoSistema config = configuracaoRepository.findById(chave)
                .orElseGet(() -> criarConfiguracaoPadrao(chave));

        // Validar o valor baseado no tipo de dado
        validarValor(chave, request.getValor());

        config.setValor(request.getValor());
        config.setUpdatedBy(getUsuarioLogado());

        config = configuracaoRepository.save(config);
        log.info("Configuração {} atualizada com sucesso para: {}", chave, request.getValor());

        return toResponse(config);
    }

    /**
     * Restaura uma configuração para o valor padrão
     */
    @Transactional
    @CacheEvict(value = "configuracoes", key = "#chave")
    public ConfiguracaoResponse restaurarPadrao(TipoConfiguracao chave) {
        log.info("Restaurando configuração {} para valor padrão", chave);

        ConfiguracaoSistema config = configuracaoRepository.findById(chave)
                .orElseGet(() -> criarConfiguracaoPadrao(chave));

        config.setValor(chave.getValorPadrao());
        config.setUpdatedBy(getUsuarioLogado());

        config = configuracaoRepository.save(config);
        log.info("Configuração {} restaurada para: {}", chave, chave.getValorPadrao());

        return toResponse(config);
    }

    /**
     * Inicializa todas as configurações com valores padrão
     */
    @Transactional
    @CacheEvict(value = "configuracoes", allEntries = true)
    public void inicializarConfiguracoesPadrao() {
        log.info("Inicializando configurações padrão do sistema");

        for (TipoConfiguracao tipo : TipoConfiguracao.values()) {
            if (!configuracaoRepository.existsById(tipo)) {
                ConfiguracaoSistema config = criarConfiguracaoPadrao(tipo);
                configuracaoRepository.save(config);
                log.debug("Configuração {} criada com valor padrão", tipo);
            }
        }

        log.info("Configurações padrão inicializadas com sucesso");
    }

    // ========== MÉTODOS UTILITÁRIOS PARA ACESSO FÁCIL ==========

    /**
     * Obtém valor de configuração como String
     */
    @Cacheable(value = "configuracoes", key = "#chave")
    public String getValorString(TipoConfiguracao chave) {
        ConfiguracaoSistema config = configuracaoRepository.findById(chave)
                .orElseGet(() -> criarConfiguracaoPadrao(chave));
        return config.getValor();
    }

    /**
     * Obtém valor de configuração como Integer
     */
    @Cacheable(value = "configuracoes", key = "#chave")
    public Integer getValorInteger(TipoConfiguracao chave) {
        String valor = getValorString(chave);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            log.error("Erro ao converter configuração {} para Integer: {}", chave, valor, e);
            return Integer.parseInt(chave.getValorPadrao());
        }
    }

    /**
     * Obtém valor de configuração como Boolean
     */
    @Cacheable(value = "configuracoes", key = "#chave")
    public Boolean getValorBoolean(TipoConfiguracao chave) {
        String valor = getValorString(chave);
        return Boolean.parseBoolean(valor);
    }

    /**
     * Obtém valor de configuração como BigDecimal
     */
    @Cacheable(value = "configuracoes", key = "#chave")
    public BigDecimal getValorDecimal(TipoConfiguracao chave) {
        String valor = getValorString(chave);
        try {
            return new BigDecimal(valor);
        } catch (NumberFormatException e) {
            log.error("Erro ao converter configuração {} para BigDecimal: {}", chave, valor, e);
            return new BigDecimal(chave.getValorPadrao());
        }
    }

    // ========== MÉTODOS PRIVADOS ==========

    /**
     * Cria uma configuração com valores padrão
     */
    private ConfiguracaoSistema criarConfiguracaoPadrao(TipoConfiguracao tipo) {
        log.debug("Criando configuração padrão para: {}", tipo);
        return ConfiguracaoSistema.builder()
                .chave(tipo)
                .valor(tipo.getValorPadrao())
                .nome(tipo.getNome())
                .descricao(tipo.getDescricao())
                .tipoDado(tipo.getTipoDado())
                .valorPadrao(tipo.getValorPadrao())
                .updatedBy("SYSTEM")
                .build();
    }

    /**
     * Valida o valor baseado no tipo de dado
     */
    private void validarValor(TipoConfiguracao chave, String valor) {
        String tipoDado = chave.getTipoDado();

        try {
            switch (tipoDado) {
                case "INTEGER":
                    Integer.parseInt(valor);
                    break;
                case "DECIMAL":
                    new BigDecimal(valor);
                    break;
                case "BOOLEAN":
                    if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false")) {
                        throw new IllegalArgumentException("Valor booleano inválido");
                    }
                    break;
                case "TEXT":
                    if (valor == null || valor.trim().isEmpty()) {
                        throw new IllegalArgumentException("Texto não pode ser vazio");
                    }
                    break;
                default:
                    log.warn("Tipo de dado desconhecido: {}", tipoDado);
            }
        } catch (Exception e) {
            log.error("Erro na validação da configuração {}: {}", chave, e.getMessage());
            throw new IllegalArgumentException(
                String.format("Valor inválido para configuração %s (tipo: %s): %s",
                    chave.getNome(), tipoDado, e.getMessage())
            );
        }
    }

    /**
     * Obtém o usuário logado
     */
    private String getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return "SYSTEM";
    }

    /**
     * Converte entidade para DTO de resposta
     */
    private ConfiguracaoResponse toResponse(ConfiguracaoSistema config) {
        return ConfiguracaoResponse.builder()
                .chave(config.getChave())
                .valor(config.getValor())
                .nome(config.getNome())
                .descricao(config.getDescricao())
                .tipoDado(config.getTipoDado())
                .valorPadrao(config.getValorPadrao())
                .updatedAt(config.getUpdatedAt())
                .updatedBy(config.getUpdatedBy())
                .build();
    }
}
