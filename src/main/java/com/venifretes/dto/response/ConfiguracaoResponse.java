package com.venifretes.dto.response;

import com.venifretes.model.enums.TipoConfiguracao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoResponse {
    private TipoConfiguracao chave;
    private String valor;
    private String nome;
    private String descricao;
    private String tipoDado;
    private String valorPadrao;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
