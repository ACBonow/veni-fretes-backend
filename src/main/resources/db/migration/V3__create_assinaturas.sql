CREATE TABLE assinaturas (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    plano_id VARCHAR(20) NOT NULL REFERENCES planos(id),
    status VARCHAR(30) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    valor_mensal DECIMAL(10,2) NOT NULL,
    metodo_pagamento VARCHAR(20),
    pagamento_recorrente BOOLEAN DEFAULT FALSE,
    id_assinatura_pagbank VARCHAR(255),
    em_periodo_teste BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    cancelada_em TIMESTAMP,
    motivo_cancelamento TEXT
);

CREATE INDEX idx_assinatura_freteiro ON assinaturas(freteiro_id);
CREATE INDEX idx_assinatura_status ON assinaturas(status);
