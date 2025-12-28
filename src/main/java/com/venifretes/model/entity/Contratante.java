package com.venifretes.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contratantes")
@PrimaryKeyJoinColumn(name = "pessoa_id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Contratante extends Pessoa {

    @Column(name = "cpf_cnpj", length = 18)
    private String cpfCnpj;

    @Column(name = "total_avaliacoes_feitas")
    private Integer totalAvaliacoesFeitas = 0;
}
