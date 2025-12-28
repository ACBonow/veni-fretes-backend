package com.venifretes.model.enums;

import lombok.Getter;

@Getter
public enum PlanoTipo {
    BASICO("Básico", 1),
    PADRAO("Padrão", 2),
    PREMIUM("Premium", 3),
    MASTER("Master", 4);

    private final String nome;
    private final int ordem;

    PlanoTipo(String nome, int ordem) {
        this.nome = nome;
        this.ordem = ordem;
    }
}
