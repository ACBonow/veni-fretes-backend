-- Fix estado column type from bytea to varchar(2) with proper bytea conversion
-- This migration handles the case where V8 didn't work due to bytea conversion issues

DO $$
BEGIN
    -- Check if column is still bytea
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'freteiros'
        AND column_name = 'estado'
        AND data_type = 'bytea'
    ) THEN
        -- Convert bytea to varchar using encode
        ALTER TABLE freteiros
        ALTER COLUMN estado TYPE VARCHAR(2)
        USING COALESCE(encode(estado, 'escape'), 'XX')::VARCHAR(2);

        RAISE NOTICE 'Column estado converted from bytea to VARCHAR(2)';
    ELSE
        RAISE NOTICE 'Column estado is already VARCHAR, skipping';
    END IF;
END $$;
