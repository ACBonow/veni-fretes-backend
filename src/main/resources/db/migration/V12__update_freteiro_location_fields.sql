-- V12: Update freteiro table to add foreign key reference to cidade

-- Add foreign key reference to cidade (nullable for backward compatibility during migration)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name = 'freteiros' AND column_name = 'cidade_id') THEN
        ALTER TABLE freteiros ADD COLUMN cidade_id INTEGER;
    END IF;
END $$;

-- Add foreign key constraint if not exists
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints
                   WHERE constraint_name = 'fk_freteiro_cidade') THEN
        ALTER TABLE freteiros ADD CONSTRAINT fk_freteiro_cidade
            FOREIGN KEY (cidade_id) REFERENCES cidades(id);
    END IF;
END $$;

-- Create index if not exists
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes
                   WHERE indexname = 'idx_freteiros_cidade_id') THEN
        CREATE INDEX idx_freteiros_cidade_id ON freteiros(cidade_id);
    END IF;
END $$;

-- Keep old cidade/estado columns for now (migration safety and rollback support)
-- They will be deprecated after successful migration

-- Add comments to mark fields as deprecated
COMMENT ON COLUMN freteiros.cidade IS 'DEPRECATED: Use cidade_id instead. Will be removed in future version.';
COMMENT ON COLUMN freteiros.estado IS 'DEPRECATED: Use cidade.estado_id instead. Will be removed in future version.';
