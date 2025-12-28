package com.venifretes.repository;

import com.venifretes.model.entity.Assinatura;
import com.venifretes.model.entity.Freteiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    @Query("""
        SELECT a FROM Assinatura a
        WHERE a.freteiro = :freteiro
        AND a.status IN ('ATIVA', 'EM_PERIODO_TESTE')
        ORDER BY a.createdAt DESC
        LIMIT 1
    """)
    Optional<Assinatura> findAssinaturaAtivaByFreteiro(@Param("freteiro") Freteiro freteiro);

    List<Assinatura> findByFreteiroOrderByCreatedAtDesc(Freteiro freteiro);

    @Query("""
        SELECT a FROM Assinatura a
        WHERE a.status = 'ATIVA'
        AND a.dataFim <= :dataLimite
        AND a.pagamentoRecorrente = false
    """)
    List<Assinatura> findAssinaturasProximasVencimento(@Param("dataLimite") LocalDate dataLimite);

    Optional<Assinatura> findByIdAssinaturaPagBank(String idAssinaturaPagBank);
}
