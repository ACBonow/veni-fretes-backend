package com.venifretes.dto.request;

import com.venifretes.model.enums.TipoEvento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TrackingRequest {
    @NotNull(message = "ID do freteiro é obrigatório")
    private Long freteiroId;

    @NotNull(message = "Tipo de evento é obrigatório")
    private TipoEvento tipo;

    private String ip;
    private String userAgent;
    private String origem;
    private String referer;
}
