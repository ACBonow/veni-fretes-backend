package com.venifretes.dto.request;

import com.venifretes.model.enums.TipoServico;
import com.venifretes.model.enums.TipoVeiculo;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePerfilRequest {

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    private String telefone;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    private String estado;

    private List<String> areasAtendidas;

    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    private String descricao;

    private List<TipoVeiculo> tiposVeiculo;

    private List<TipoServico> tiposServico;
}
