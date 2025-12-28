package com.venifretes.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AvaliacaoRequest {

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    private Integer nota;

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nomeAvaliador;

    @Size(max = 1000, message = "Comentário deve ter no máximo 1000 caracteres")
    private String comentario;
}
