-- Script para resetar apenas a migration V14 que falhou

-- 1. Deletar registros criados (se existirem parcialmente)
DELETE FROM usuarios WHERE usuario_id IN (
    SELECT id FROM pessoas WHERE email = 'admin@venifretes.com'
);
DELETE FROM pessoas WHERE email = 'admin@venifretes.com';

-- 2. Limpar histórico do Flyway para V14
DELETE FROM flyway_schema_history WHERE version = '14';

-- Pronto! Agora reinicie a aplicação
