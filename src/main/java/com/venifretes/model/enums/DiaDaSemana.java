package com.venifretes.model.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DiaDaSemana {
    SEGUNDA("Segunda-feira", "SEG", 1),
    TERCA("Terça-feira", "TER", 2),
    QUARTA("Quarta-feira", "QUA", 3),
    QUINTA("Quinta-feira", "QUI", 4),
    SEXTA("Sexta-feira", "SEX", 5),
    SABADO("Sábado", "SAB", 6),
    DOMINGO("Domingo", "DOM", 7),
    FERIADO("Feriados", "FER", 8);

    private final String nome;
    private final String sigla;
    private final int ordem;

    DiaDaSemana(String nome, String sigla, int ordem) {
        this.nome = nome;
        this.sigla = sigla;
        this.ordem = ordem;
    }

    /**
     * Parse from old database format
     * "Segunda a Sexta, Sábado, Domingo, Feriados" -> [SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO, FERIADO]
     * "Segunda a Sexta" -> [SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA]
     *
     * @param dias Old format comma-separated string
     * @return List of DiaDaSemana enums (empty list if null/empty input)
     */
    public static List<DiaDaSemana> parseFromOldFormat(String dias) {
        if (dias == null || dias.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<DiaDaSemana> result = new ArrayList<>();
        String[] parts = dias.split(",");

        for (String part : parts) {
            String trimmed = part.trim().toLowerCase();

            // Handle "Segunda a Sexta" or "Segunda à Sexta"
            if (trimmed.contains("segunda a sexta") || trimmed.contains("segunda à sexta")) {
                result.addAll(Arrays.asList(SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA));
            }
            // Handle individual days
            else if (trimmed.contains("segunda")) {
                result.add(SEGUNDA);
            } else if (trimmed.contains("terça") || trimmed.contains("terca")) {
                result.add(TERCA);
            } else if (trimmed.contains("quarta")) {
                result.add(QUARTA);
            } else if (trimmed.contains("quinta")) {
                result.add(QUINTA);
            } else if (trimmed.contains("sexta")) {
                result.add(SEXTA);
            } else if (trimmed.contains("sábado") || trimmed.contains("sabado")) {
                result.add(SABADO);
            } else if (trimmed.contains("domingo")) {
                result.add(DOMINGO);
            } else if (trimmed.contains("feriado")) {
                result.add(FERIADO);
            }
        }

        // Remove duplicates and return
        return result.stream().distinct().collect(Collectors.toList());
    }
}
