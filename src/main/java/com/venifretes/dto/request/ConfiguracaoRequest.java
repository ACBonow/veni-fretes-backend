package com.venifretes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoRequest {

    @NotBlank(message = "Valor é obrigatório")
    private String valor;
}
