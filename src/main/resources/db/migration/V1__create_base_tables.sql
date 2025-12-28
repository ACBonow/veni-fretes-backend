-- Tabela base: pessoas
CREATE TABLE pessoas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela: usuarios (herda de pessoas)
CREATE TABLE usuarios (
    pessoa_id BIGINT PRIMARY KEY REFERENCES pessoas(id) ON DELETE CASCADE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    email_verificado BOOLEAN DEFAULT FALSE,
    last_login_at TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

-- Tabela: freteiros (herda de usuarios)
CREATE TABLE freteiros (
    usuario_id BIGINT PRIMARY KEY REFERENCES usuarios(pessoa_id) ON DELETE CASCADE,
    slug VARCHAR(100) NOT NULL UNIQUE,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    areas_atendidas JSONB DEFAULT '[]'::jsonb,
    descricao TEXT,
    foto_perfil VARCHAR(255),
    fotos_veiculo JSONB DEFAULT '[]'::jsonb,
    porcentagem_completude INTEGER DEFAULT 0,
    avaliacao_media DECIMAL(3,2) DEFAULT 0.00,
    total_avaliacoes INTEGER DEFAULT 0,
    total_visualizacoes BIGINT DEFAULT 0,
    total_cliques BIGINT DEFAULT 0,
    cliques_whatsapp BIGINT DEFAULT 0,
    cliques_telefone BIGINT DEFAULT 0,
    verificado BOOLEAN DEFAULT FALSE,
    data_verificacao TIMESTAMP
);

CREATE INDEX idx_freteiro_slug ON freteiros(slug);
CREATE INDEX idx_freteiro_cidade_estado ON freteiros(cidade, estado);
CREATE INDEX idx_freteiro_verificado ON freteiros(verificado);

-- Tabela auxiliar: tipos de veículo do freteiro
CREATE TABLE freteiro_tipos_veiculo (
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    tipo_veiculo VARCHAR(30) NOT NULL,
    PRIMARY KEY (freteiro_id, tipo_veiculo)
);

-- Tabela auxiliar: tipos de serviço do freteiro
CREATE TABLE freteiro_tipos_servico (
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    tipo_servico VARCHAR(30) NOT NULL,
    PRIMARY KEY (freteiro_id, tipo_servico)
);

-- Tabela: admins (herda de usuarios)
CREATE TABLE admins (
    usuario_id BIGINT PRIMARY KEY REFERENCES usuarios(pessoa_id) ON DELETE CASCADE,
    nivel_acesso INTEGER DEFAULT 1,
    super_admin BOOLEAN DEFAULT FALSE
);

-- Tabela: contratantes (herda de pessoas)
CREATE TABLE contratantes (
    pessoa_id BIGINT PRIMARY KEY REFERENCES pessoas(id) ON DELETE CASCADE,
    cpf_cnpj VARCHAR(18),
    total_avaliacoes_feitas INTEGER DEFAULT 0
);
