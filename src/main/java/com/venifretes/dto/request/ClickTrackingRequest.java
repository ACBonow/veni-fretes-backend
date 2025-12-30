package com.venifretes.dto.request;

import com.venifretes.model.enums.TipoEvento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClickTrackingRequest {
    @NotNull(message = "ID do freteiro é obrigatório")
    private Long freteiroId;

    private TipoEvento tipo; // Opcional: CLIQUE_WHATSAPP, CLIQUE_TELEFONE, etc. Default: CLIQUE_WHATSAPP

    private String origem;
    private String referer;
}
