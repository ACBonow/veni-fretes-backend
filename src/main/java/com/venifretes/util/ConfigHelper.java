package com.venifretes.util;

import com.venifretes.model.enums.TipoConfiguracao;
import com.venifretes.service.configuracao.ConfiguracaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Helper class para acessar facilmente as configurações do sistema.
 * Esta classe substitui a antiga classe Constants, permitindo que as configurações
 * sejam editáveis pelo administrador em tempo de execução.
 *
 * Uso:
 * @Autowired private ConfigHelper configHelper;
 * int periodoTeste = configHelper.getPeriodoTesteDias();
 */
@Component
@RequiredArgsConstructor
public class ConfigHelper {

    private final ConfiguracaoService configuracaoService;

    // ========== CONFIGURAÇÕES DE PERÍODO DE TESTE ==========

    public Integer getPeriodoTesteDias() {
        return configuracaoService.getValorInteger(TipoConfiguracao.PERIODO_TESTE_DIAS);
    }

    // ========== CONFIGURAÇÕES DE VALIDAÇÃO DE FOTOS ==========

    public Integer getMinFotosVeiculo() {
        return configuracaoService.getValorInteger(TipoConfiguracao.MIN_FOTOS_VEICULO);
    }

    public Integer getMaxFileSizeMB() {
        return configuracaoService.getValorInteger(TipoConfiguracao.MAX_FILE_SIZE_MB);
    }

    // ========== CONFIGURAÇÕES DE VALIDAÇÃO DE TEXTO ==========

    public Integer getMinDescricaoLength() {
        return configuracaoService.getValorInteger(TipoConfiguracao.MIN_DESCRICAO_LENGTH);
    }

    // ========== MENSAGENS AUTOMÁTICAS ==========

    public String getMensagemWhatsAppContratante() {
        return configuracaoService.getValorString(TipoConfiguracao.MENSAGEM_WHATSAPP_CONTRATANTE);
    }

    public String getMensagemBoasVindasFreteiro() {
        return configuracaoService.getValorString(TipoConfiguracao.MENSAGEM_BOAS_VINDAS_FRETEIRO);
    }

    public String getMensagemBoasVindasContratante() {
        return configuracaoService.getValorString(TipoConfiguracao.MENSAGEM_BOAS_VINDAS_CONTRATANTE);
    }

    public String getMensagemExpiracaoTeste(int dias) {
        return configuracaoService.getValorString(TipoConfiguracao.MENSAGEM_EXPIRACAO_TESTE)
                .replace("{dias}", String.valueOf(dias));
    }

    public String getMensagemAssinaturaExpirada() {
        return configuracaoService.getValorString(TipoConfiguracao.MENSAGEM_ASSINATURA_EXPIRADA);
    }

    // ========== CONFIGURAÇÕES DE NOTIFICAÇÕES ==========

    public Boolean isNotificacoesEmailHabilitadas() {
        return configuracaoService.getValorBoolean(TipoConfiguracao.HABILITAR_NOTIFICACOES_EMAIL);
    }

    public Boolean isNotificacoesWhatsAppHabilitadas() {
        return configuracaoService.getValorBoolean(TipoConfiguracao.HABILITAR_NOTIFICACOES_WHATSAPP);
    }

    // ========== CONFIGURAÇÕES DE RANKING ==========

    public Integer getDiasInatividadePenalizacao() {
        return configuracaoService.getValorInteger(TipoConfiguracao.DIAS_INATIVIDADE_PENALIZACAO);
    }

    // ========== CONFIGURAÇÕES DE PONTOS ==========

    public BigDecimal getValorPontoReais() {
        return configuracaoService.getValorDecimal(TipoConfiguracao.VALOR_PONTO_REAIS);
    }

    public Integer getPontosPorAvaliacao() {
        return configuracaoService.getValorInteger(TipoConfiguracao.PONTOS_POR_AVALIACAO);
    }
}
