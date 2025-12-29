package com.venifretes.repository;

import com.venifretes.model.entity.Cidade;
import com.venifretes.model.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    Optional<Cidade> findByCodigoIbge(Integer codigoIbge);

    Optional<Cidade> findByEstadoAndNome(Estado estado, String nome);

    @Query("SELECT c FROM Cidade c WHERE c.estado.sigla = :sigla AND LOWER(c.nome) = LOWER(:nome)")
    Optional<Cidade> findByEstadoSiglaAndNomeIgnoreCase(
        @Param("sigla") String sigla,
        @Param("nome") String nome
    );

    List<Cidade> findByEstado(Estado estado);

    @Query("SELECT c FROM Cidade c WHERE c.estado.sigla = :sigla ORDER BY c.nome")
    List<Cidade> findByEstadoSiglaOrderByNome(@Param("sigla") String sigla);

    long countByEstado(Estado estado);
}
