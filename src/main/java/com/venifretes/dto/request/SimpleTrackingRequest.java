package com.venifretes.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleTrackingRequest {
    @NotNull(message = "ID do freteiro é obrigatório")
    private Long freteiroId;

    private String origem;
    private String referer;
}
