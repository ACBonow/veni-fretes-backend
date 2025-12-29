-- V14: Create default admin user for system administration

-- Insert into pessoas table (base entity) - use ON CONFLICT to make it idempotent
INSERT INTO pessoas (nome, telefone, email, created_at, updated_at)
VALUES ('Administrador', '53000000000', 'admin@venifretes.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insert into usuarios table (with ADMIN role) - use ON CONFLICT to make it idempotent
-- Password: admin123 (BCrypt hash)
INSERT INTO usuarios (pessoa_id, password, role, email_verificado, ativo)
SELECT
    p.id,
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCu', -- BCrypt hash for "admin123"
    'ADMIN',
    true,
    true
FROM pessoas p
WHERE p.email = 'admin@venifretes.com'
ON CONFLICT (pessoa_id) DO NOTHING;

-- Note: Default credentials are:
--   Email: admin@venifretes.com
--   Password: admin123
--   IMPORTANT: Change this password in production!
