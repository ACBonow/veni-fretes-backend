-- Fix estado column type from bytea to varchar(2)
ALTER TABLE freteiros
ALTER COLUMN estado TYPE VARCHAR(2) USING estado::text;
