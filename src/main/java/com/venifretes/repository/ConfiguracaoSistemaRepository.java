package com.venifretes.repository;

import com.venifretes.model.entity.ConfiguracaoSistema;
import com.venifretes.model.enums.TipoConfiguracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoSistemaRepository extends JpaRepository<ConfiguracaoSistema, TipoConfiguracao> {
}
