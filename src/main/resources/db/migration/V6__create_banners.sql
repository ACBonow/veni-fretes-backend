CREATE TABLE banners (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    imagem_url VARCHAR(255) NOT NULL,
    link TEXT,
    localizacao VARCHAR(50),
    ativo BOOLEAN DEFAULT TRUE,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    visualizacoes BIGINT DEFAULT 0,
    cliques BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
