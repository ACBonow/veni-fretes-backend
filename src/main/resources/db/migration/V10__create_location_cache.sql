-- V10: Create location cache tables (estados and cidades)

-- Create estados (Brazilian states) cache table
CREATE TABLE estados (
    id SERIAL PRIMARY KEY,
    sigla VARCHAR(2) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    codigo_ibge INTEGER NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_estados_sigla ON estados(sigla);

-- Create cidades (Brazilian cities) cache table
CREATE TABLE cidades (
    id SERIAL PRIMARY KEY,
    estado_id INTEGER NOT NULL REFERENCES estados(id),
    nome VARCHAR(100) NOT NULL,
    codigo_ibge INTEGER NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cidades_estado_id ON cidades(estado_id);
CREATE INDEX idx_cidades_nome ON cidades(nome);
CREATE INDEX idx_cidades_codigo_ibge ON cidades(codigo_ibge);
CREATE UNIQUE INDEX idx_cidades_estado_nome ON cidades(estado_id, nome);

-- Insert Rio Grande do Sul (will be used for IBGE import)
INSERT INTO estados (sigla, nome, codigo_ibge) VALUES ('RS', 'Rio Grande do Sul', 43);

-- Note: Cities will be imported via Java service from IBGE API
