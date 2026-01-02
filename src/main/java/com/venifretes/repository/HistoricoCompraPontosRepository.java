package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.HistoricoCompraPontos;
import com.venifretes.model.enums.StatusPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricoCompraPontosRepository extends JpaRepository<HistoricoCompraPontos, Long> {

    Optional<HistoricoCompraPontos> findByTransacaoId(String transacaoId);

    Optional<HistoricoCompraPontos> findByPagbankChargeId(String pagbankChargeId);

    Page<HistoricoCompraPontos> findByFreteiro(Freteiro freteiro, Pageable pageable);

    @Query("SELECT h FROM HistoricoCompraPontos h WHERE h.freteiro.id = :freteiroId ORDER BY h.createdAt DESC")
    Page<HistoricoCompraPontos> findByFreteiroIdOrderByCreatedAtDesc(@Param("freteiroId") Long freteiroId,
                                                                       Pageable pageable);

    @Query("SELECT h FROM HistoricoCompraPontos h WHERE h.freteiro.id = :freteiroId AND h.cidade = :cidade ORDER BY h.createdAt DESC")
    Page<HistoricoCompraPontos> findByFreteiroIdAndCidade(@Param("freteiroId") Long freteiroId,
                                                            @Param("cidade") String cidade,
                                                            Pageable pageable);

    List<HistoricoCompraPontos> findByStatus(StatusPagamento status);

    @Query("SELECT h FROM HistoricoCompraPontos h WHERE h.status = :status AND h.createdAt > :dataInicio")
    List<HistoricoCompraPontos> findByStatusAndCreatedAtAfter(@Param("status") StatusPagamento status,
                                                                @Param("dataInicio") LocalDateTime dataInicio);

    @Query("SELECT SUM(h.valorTotal) FROM HistoricoCompraPontos h WHERE h.status = 'APROVADO' AND h.dataPagamento BETWEEN :inicio AND :fim")
    BigDecimal getTotalReceitaPeriodo(@Param("inicio") LocalDateTime inicio,
                                       @Param("fim") LocalDateTime fim);

    @Query("SELECT SUM(h.quantidadePontos) FROM HistoricoCompraPontos h WHERE h.freteiro.id = :freteiroId AND h.status = 'APROVADO'")
    Integer getTotalPontosCompradosByFreteiro(@Param("freteiroId") Long freteiroId);

    @Query("SELECT COUNT(h) FROM HistoricoCompraPontos h WHERE h.cidade = :cidade AND h.status = 'APROVADO' AND h.dataPagamento BETWEEN :inicio AND :fim")
    Long countComprasPorCidadeNoPeriodo(@Param("cidade") String cidade,
                                         @Param("inicio") LocalDateTime inicio,
                                         @Param("fim") LocalDateTime fim);

    @Query("SELECT h FROM HistoricoCompraPontos h WHERE h.status IN ('PENDENTE', 'PROCESSANDO') AND h.createdAt < :dataLimite")
    List<HistoricoCompraPontos> findPagamentosPendentesExpirados(@Param("dataLimite") LocalDateTime dataLimite);

    // Admin statistics queries
    @Query("SELECT COALESCE(SUM(h.valorTotal), 0) FROM HistoricoCompraPontos h WHERE h.status = 'APROVADO'")
    BigDecimal getTotalReceitaPontos();

    @Query("SELECT COALESCE(SUM(h.quantidadePontos), 0) FROM HistoricoCompraPontos h WHERE h.status = 'APROVADO'")
    Integer getTotalPontosVendidos();
}
