package com.venifretes.repository;

import com.venifretes.model.entity.Assinatura;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.enums.StatusAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Admin statistics queries
    long countByStatus(StatusAssinatura status);

    @Query("SELECT COALESCE(SUM(a.valorMensal), 0) FROM Assinatura a WHERE a.status IN ('ATIVA', 'EM_PERIODO_TESTE')")
    BigDecimal getTotalReceitaAssinaturas();

    @Query("""
        SELECT COALESCE(SUM(a.valorMensal), 0) FROM Assinatura a
        WHERE a.status IN ('ATIVA', 'EM_PERIODO_TESTE')
        AND a.dataInicio >= :dataInicio
    """)
    BigDecimal getReceitaPeriodo(@Param("dataInicio") LocalDateTime dataInicio);
}
