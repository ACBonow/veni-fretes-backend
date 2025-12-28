CREATE TABLE planos (
    id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    features JSONB,
    posicao_ranking INTEGER NOT NULL,
    limite_fotos INTEGER NOT NULL,
    ordem INTEGER NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- Seed inicial de planos
INSERT INTO planos (id, nome, preco, posicao_ranking, limite_fotos, ordem, features) VALUES
('BASICO', 'Básico', 0.00, 1, 3, 1, '{"destaque": false, "suporte": "email"}'::jsonb),
('PADRAO', 'Padrão', 49.90, 2, 5, 2, '{"destaque": true, "suporte": "prioritário"}'::jsonb),
('PREMIUM', 'Premium', 99.90, 3, 10, 3, '{"destaque": true, "selo_premium": true}'::jsonb),
('MASTER', 'Master', 199.90, 4, 20, 4, '{"topo_ranking": true, "selo_master": true}'::jsonb);
