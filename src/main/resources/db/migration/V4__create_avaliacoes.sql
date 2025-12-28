CREATE TABLE avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    usuario_id BIGINT REFERENCES usuarios(pessoa_id) ON DELETE SET NULL,
    nome_avaliador VARCHAR(100),
    nota INTEGER NOT NULL CHECK (nota >= 1 AND nota <= 5),
    comentario TEXT,
    resposta_freteiro TEXT,
    data_resposta TIMESTAMP,
    aprovado BOOLEAN DEFAULT FALSE,
    denunciado BOOLEAN DEFAULT FALSE,
    motivo_denuncia TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_avaliacao_freteiro ON avaliacoes(freteiro_id);
CREATE INDEX idx_avaliacao_usuario ON avaliacoes(usuario_id);
CREATE INDEX idx_avaliacao_aprovado ON avaliacoes(aprovado);
