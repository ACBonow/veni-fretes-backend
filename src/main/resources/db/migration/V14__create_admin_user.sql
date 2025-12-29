-- V14: Create default admin user for system administration

-- Insert into pessoas table (base entity)
INSERT INTO pessoas (nome, telefone, email, created_at, updated_at)
VALUES ('Administrador', '53000000000', 'admin@venifretes.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert into usuarios table (with ADMIN role)
-- Password: admin123 (BCrypt hash)
INSERT INTO usuarios (usuario_id, password, role, email_verificado, ativo, created_at, updated_at)
VALUES (
    (SELECT id FROM pessoas WHERE email = 'admin@venifretes.com'),
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', -- BCrypt hash for "admin123"
    'ADMIN',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Note: Default credentials are:
--   Email: admin@venifretes.com
--   Password: admin123
--   IMPORTANT: Change this password in production!
