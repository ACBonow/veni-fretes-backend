-- Tabela de Pontos de Ranking por Cidade
CREATE TABLE pontos_ranking (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    pontos_ativos INTEGER NOT NULL DEFAULT 0,
    pontos_gastos INTEGER DEFAULT 0,
    total_pontos_comprados INTEGER DEFAULT 0,
    expira_em TIMESTAMP,
    ultima_compra TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pontos_freteiro FOREIGN KEY (freteiro_id)
        REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    CONSTRAINT uq_freteiro_cidade UNIQUE (freteiro_id, cidade),
    CONSTRAINT chk_pontos_ativos_positive CHECK (pontos_ativos >= 0)
);

-- Índices para performance
CREATE INDEX idx_pontos_freteiro ON pontos_ranking(freteiro_id);
CREATE INDEX idx_pontos_cidade ON pontos_ranking(cidade);
CREATE INDEX idx_pontos_ativos ON pontos_ranking(pontos_ativos);
CREATE INDEX idx_pontos_expiracao ON pontos_ranking(expira_em) WHERE expira_em IS NOT NULL;

-- Tabela de Histórico de Compras de Pontos
CREATE TABLE historico_compra_pontos (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    quantidade_pontos INTEGER NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    desconto_percentual DECIMAL(5, 2) DEFAULT 0,
    metodo_pagamento VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    transacao_id VARCHAR(100) UNIQUE,
    pagbank_charge_id VARCHAR(100),
    qr_code_pix TEXT,
    link_pagamento TEXT,
    posicao_estimada_antes INTEGER,
    posicao_estimada_depois INTEGER,
    dias_validade INTEGER DEFAULT 30,
    data_pagamento TIMESTAMP,
    data_expiracao TIMESTAMP,
    observacoes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_compra_freteiro FOREIGN KEY (freteiro_id)
        REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    CONSTRAINT chk_quantidade_pontos_positive CHECK (quantidade_pontos > 0),
    CONSTRAINT chk_valor_total_positive CHECK (valor_total > 0),
    CONSTRAINT chk_status_valido CHECK (status IN (
        'PENDENTE', 'PROCESSANDO', 'APROVADO', 'RECUSADO',
        'CANCELADO', 'ESTORNADO', 'EXPIRADO'
    ))
);

-- Índices para performance
CREATE INDEX idx_compra_freteiro ON historico_compra_pontos(freteiro_id);
CREATE INDEX idx_compra_status ON historico_compra_pontos(status);
CREATE INDEX idx_compra_transacao ON historico_compra_pontos(transacao_id);
CREATE INDEX idx_compra_created ON historico_compra_pontos(created_at);
CREATE INDEX idx_compra_cidade ON historico_compra_pontos(cidade);

-- Comentários para documentação
COMMENT ON TABLE pontos_ranking IS 'Armazena pontos extras de ranking por cidade para freteiros Master';
COMMENT ON COLUMN pontos_ranking.pontos_ativos IS 'Pontos atualmente válidos e ativos';
COMMENT ON COLUMN pontos_ranking.pontos_gastos IS 'Total histórico de pontos já expirados ou utilizados';
COMMENT ON COLUMN pontos_ranking.expira_em IS 'Data de expiração dos pontos ativos';

COMMENT ON TABLE historico_compra_pontos IS 'Histórico de todas as compras de pontos realizadas';
COMMENT ON COLUMN historico_compra_pontos.status IS 'Status do pagamento: PENDENTE, PROCESSANDO, APROVADO, RECUSADO, etc';
COMMENT ON COLUMN historico_compra_pontos.posicao_estimada_antes IS 'Posição no ranking antes da compra';
COMMENT ON COLUMN historico_compra_pontos.posicao_estimada_depois IS 'Posição estimada após aprovação da compra';
