package com.venifretes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoResponse {
    private Long id;
    private String nomeAvaliador;
    private Integer nota;
    private String comentario;
    private String respostaFreteiro;
    private LocalDateTime dataResposta;
    private LocalDateTime createdAt;
}
