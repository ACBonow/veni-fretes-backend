package com.venifretes.model.enums;

import lombok.Getter;

@Getter
public enum TipoConfiguracao {
    // Configurações de Período de Teste
    PERIODO_TESTE_DIAS("Período de Teste (dias)", "Número de dias do período de teste gratuito", "15", "INTEGER"),

    // Configurações de Validação de Fotos
    MIN_FOTOS_VEICULO("Mínimo de Fotos do Veículo", "Quantidade mínima de fotos do veículo necessárias", "3", "INTEGER"),
    MAX_FILE_SIZE_MB("Tamanho Máximo de Arquivo (MB)", "Tamanho máximo permitido para upload de arquivos em megabytes", "10", "INTEGER"),

    // Configurações de Validação de Texto
    MIN_DESCRICAO_LENGTH("Tamanho Mínimo da Descrição", "Quantidade mínima de caracteres para a descrição", "50", "INTEGER"),

    // Mensagens Automáticas - WhatsApp
    MENSAGEM_WHATSAPP_CONTRATANTE(
        "Mensagem WhatsApp - Contratante",
        "Mensagem enviada quando um contratante clica no WhatsApp do freteiro",
        "Olá! Vi seu perfil na plataforma Veni Fretes e gostaria de solicitar um orçamento.",
        "TEXT"
    ),

    // Mensagens de Boas-vindas
    MENSAGEM_BOAS_VINDAS_FRETEIRO(
        "Mensagem Boas-vindas - Freteiro",
        "Mensagem de boas-vindas enviada para novos freteiros",
        "Bem-vindo à Veni Fretes! Complete seu perfil para começar a receber propostas.",
        "TEXT"
    ),

    MENSAGEM_BOAS_VINDAS_CONTRATANTE(
        "Mensagem Boas-vindas - Contratante",
        "Mensagem de boas-vindas enviada para novos contratantes",
        "Bem-vindo à Veni Fretes! Encontre os melhores freteiros da sua região.",
        "TEXT"
    ),

    // Mensagens de Expiração
    MENSAGEM_EXPIRACAO_TESTE(
        "Mensagem Expiração - Período de Teste",
        "Mensagem enviada quando o período de teste está prestes a expirar",
        "Seu período de teste expira em {dias} dias. Assine um plano para continuar visível!",
        "TEXT"
    ),

    MENSAGEM_ASSINATURA_EXPIRADA(
        "Mensagem Assinatura Expirada",
        "Mensagem enviada quando a assinatura expira",
        "Sua assinatura expirou. Renove agora para continuar recebendo propostas!",
        "TEXT"
    ),

    // Configurações de Notificações
    HABILITAR_NOTIFICACOES_EMAIL(
        "Habilitar Notificações por Email",
        "Ativa ou desativa o envio de notificações por email",
        "true",
        "BOOLEAN"
    ),

    HABILITAR_NOTIFICACOES_WHATSAPP(
        "Habilitar Notificações por WhatsApp",
        "Ativa ou desativa o envio de notificações por WhatsApp",
        "false",
        "BOOLEAN"
    ),

    // Configurações de Ranking
    DIAS_INATIVIDADE_PENALIZACAO(
        "Dias de Inatividade para Penalização",
        "Número de dias sem login para aplicar penalização no ranking",
        "30",
        "INTEGER"
    ),

    // Configurações de Pontos
    VALOR_PONTO_REAIS(
        "Valor do Ponto em Reais",
        "Valor em reais de cada ponto para compra",
        "1.00",
        "DECIMAL"
    ),

    PONTOS_POR_AVALIACAO(
        "Pontos por Avaliação Recebida",
        "Quantidade de pontos ganhos ao receber uma avaliação",
        "10",
        "INTEGER"
    );

    private final String nome;
    private final String descricao;
    private final String valorPadrao;
    private final String tipoDado;

    TipoConfiguracao(String nome, String descricao, String valorPadrao, String tipoDado) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
        this.tipoDado = tipoDado;
    }
}
