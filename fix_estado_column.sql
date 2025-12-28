-- Script para corrigir a coluna estado manualmente
-- Execute este script diretamente no PostgreSQL se a migration não funcionar

-- Verifica o tipo atual da coluna
SELECT column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_name = 'freteiros' AND column_name = 'estado';

-- Corrige o tipo da coluna
ALTER TABLE freteiros
ALTER COLUMN estado TYPE VARCHAR(2) USING
    CASE
        WHEN estado IS NULL THEN NULL
        ELSE encode(estado, 'escape')::VARCHAR(2)
    END;

-- Verifica se foi corrigido
SELECT column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_name = 'freteiros' AND column_name = 'estado';
