package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalhes de uma avaliação para listagem administrativa")
public class ReviewListResponse {

    @Schema(description = "ID da avaliação", example = "123")
    private Long id;

    @Schema(description = "Nome do freteiro avaliado", example = "João Silva")
    private String freteiroNome;

    @Schema(description = "Slug do freteiro", example = "joao-silva-sp")
    private String freteiroSlug;

    @Schema(description = "Nome do contratante que avaliou", example = "Maria Santos")
    private String contratanteNome;

    @Schema(description = "Nota da avaliação (1-5)", example = "5")
    private Integer nota;

    @Schema(description = "Comentário da avaliação")
    private String comentario;

    @Schema(description = "Se a avaliação está aprovada", example = "true")
    private Boolean aprovado;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;
}
