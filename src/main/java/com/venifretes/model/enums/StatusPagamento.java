package com.venifretes.model.enums;

public enum StatusPagamento {
    PENDENTE,           // Aguardando pagamento
    PROCESSANDO,        // Pagamento em processamento
    APROVADO,           // Pagamento confirmado
    RECUSADO,           // Pagamento recusado
    CANCELADO,          // Pagamento cancelado
    ESTORNADO,          // Pagamento estornado
    EXPIRADO            // Pagamento expirou (PIX não pago)
}
