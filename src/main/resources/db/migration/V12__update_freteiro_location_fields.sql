-- V12: Update freteiro table to add foreign key reference to cidade

-- Add foreign key reference to cidade (nullable for backward compatibility during migration)
ALTER TABLE freteiros ADD COLUMN cidade_id INTEGER;

ALTER TABLE freteiros ADD CONSTRAINT fk_freteiro_cidade
    FOREIGN KEY (cidade_id) REFERENCES cidades(id);

CREATE INDEX idx_freteiros_cidade_id ON freteiros(cidade_id);

-- Keep old cidade/estado columns for now (migration safety and rollback support)
-- They will be deprecated after successful migration

-- Add comments to mark fields as deprecated
COMMENT ON COLUMN freteiros.cidade IS 'DEPRECATED: Use cidade_id instead. Will be removed in future version.';
COMMENT ON COLUMN freteiros.estado IS 'DEPRECATED: Use cidade.estado_id instead. Will be removed in future version.';
