package com.venifretes.dto.response;

import com.venifretes.model.enums.DiaDaSemana;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HorarioAtendimentoDTO {
    private DiaDaSemana diaSemana;
    private String horaInicio;
    private String horaFim;
}
