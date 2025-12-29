package com.venifretes.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CidadeDTO {
    private Integer id;
    private String nome;
    private String estadoSigla;
    private String estadoNome;
    private Integer codigoIbge;
}
