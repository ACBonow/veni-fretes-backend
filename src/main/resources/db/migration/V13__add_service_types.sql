-- V13: Add additional service types (marker migration)

-- No schema changes needed - enum changes are code-only
-- This migration serves as a version marker for when new service types were added:
-- - MUDANCA (replacing old boolean mudanca field)
-- - EMPACOTAMENTO (from old servicos field)
-- - TRANSPORTE_ANIMAIS (from old servicos field)

-- Add comment documenting the change
COMMENT ON TABLE freteiro_tipos_servico IS
'Service types enum expanded in V13 to include: EMPACOTAMENTO, TRANSPORTE_ANIMAIS, MUDANCA (now as service type, not separate field)';
