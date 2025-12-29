package com.venifretes.repository;

import com.venifretes.model.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    Optional<Estado> findBySigla(String sigla);

    Optional<Estado> findByCodigoIbge(Integer codigoIbge);

    boolean existsBySigla(String sigla);
}
