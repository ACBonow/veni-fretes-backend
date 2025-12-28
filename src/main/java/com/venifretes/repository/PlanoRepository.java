package com.venifretes.repository;

import com.venifretes.model.entity.Plano;
import com.venifretes.model.enums.PlanoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, PlanoTipo> {
    List<Plano> findAllByAtivoTrueOrderByOrdem();
}
