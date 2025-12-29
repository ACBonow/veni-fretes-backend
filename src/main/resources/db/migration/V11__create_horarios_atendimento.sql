-- V11: Create horarios_atendimento table for multiple work shifts per day

CREATE TABLE horarios_atendimento (
    id BIGSERIAL PRIMARY KEY,
    freteiro_id BIGINT NOT NULL REFERENCES freteiros(usuario_id) ON DELETE CASCADE,
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio VARCHAR(5) NOT NULL, -- Format: "HH:MM" (e.g., "08:00")
    hora_fim VARCHAR(5) NOT NULL,    -- Format: "HH:MM" (e.g., "12:00")
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_horarios_freteiro ON horarios_atendimento(freteiro_id);
CREATE INDEX idx_horarios_dia ON horarios_atendimento(dia_semana);

-- Add constraints to ensure valid time format (15-minute increments only)
ALTER TABLE horarios_atendimento
ADD CONSTRAINT chk_hora_inicio_format
CHECK (hora_inicio ~ '^([0-1][0-9]|2[0-3]):(00|15|30|45)$');

ALTER TABLE horarios_atendimento
ADD CONSTRAINT chk_hora_fim_format
CHECK (hora_fim ~ '^([0-1][0-9]|2[0-3]):(00|15|30|45)$');

-- Note: hora_fim > hora_inicio validation will be done in application logic
