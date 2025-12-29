-- Script para resetar as migrations V10-V14
-- Execute este script no seu banco PostgreSQL

-- 1. Remover usuário admin (se existir)
DELETE FROM usuarios WHERE pessoa_id IN (SELECT id FROM pessoas WHERE email = 'admin@venifretes.com');
DELETE FROM pessoas WHERE email = 'admin@venifretes.com';

-- 2. Remover tabelas criadas pelas migrations
DROP TABLE IF EXISTS horarios_atendimento CASCADE;
DROP TABLE IF EXISTS cidades CASCADE;
DROP TABLE IF EXISTS estados CASCADE;

-- 3. Remover constraint de freteiros (se existir)
ALTER TABLE freteiros DROP CONSTRAINT IF EXISTS fk_freteiro_cidade;

-- 4. Remover coluna cidade_id de freteiros (se existir)
ALTER TABLE freteiros DROP COLUMN IF EXISTS cidade_id;

-- 5. Limpar histórico do Flyway para V10-V14
DELETE FROM flyway_schema_history WHERE version IN ('10', '11', '12', '13', '14');

-- Pronto! Agora reinicie a aplicação que o Flyway vai re-executar V10-V14
