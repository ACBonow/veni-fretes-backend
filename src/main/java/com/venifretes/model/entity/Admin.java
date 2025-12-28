package com.venifretes.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admin extends Usuario {

    @Column(name = "nivel_acesso")
    private Integer nivelAcesso = 1;

    @Column(name = "super_admin")
    private Boolean superAdmin = false;
}
