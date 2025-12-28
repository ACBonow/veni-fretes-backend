CREATE TABLE eventos_tracking (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    tipo VARCHAR(30) NOT NULL,
    ip VARCHAR(45),
    user_agent TEXT,
    origem VARCHAR(100),
    referer TEXT,
    notificacao_enviada BOOLEAN DEFAULT FALSE,
    data_notificacao TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_evento_freteiro ON eventos_tracking(freteiro_id);
CREATE INDEX idx_evento_tipo ON eventos_tracking(tipo);
CREATE INDEX idx_evento_created ON eventos_tracking(created_at);
