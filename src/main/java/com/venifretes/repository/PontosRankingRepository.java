package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.PontosRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PontosRankingRepository extends JpaRepository<PontosRanking, Long> {

    Optional<PontosRanking> findByFreteiroAndCidade(Freteiro freteiro, String cidade);

    @Query("SELECT pr FROM PontosRanking pr WHERE pr.freteiro.id = :freteiroId AND pr.cidade = :cidade")
    Optional<PontosRanking> findByFreteiroIdAndCidade(@Param("freteiroId") Long freteiroId,
                                                        @Param("cidade") String cidade);

    List<PontosRanking> findByFreteiro(Freteiro freteiro);

    @Query("SELECT pr FROM PontosRanking pr WHERE pr.freteiro.id = :freteiroId")
    List<PontosRanking> findAllByFreteiroId(@Param("freteiroId") Long freteiroId);

    @Query("SELECT pr FROM PontosRanking pr WHERE pr.cidade = :cidade AND pr.pontosAtivos > 0 ORDER BY pr.pontosAtivos DESC")
    List<PontosRanking> findByCidadeOrderByPontosDesc(@Param("cidade") String cidade);

    @Query("SELECT pr FROM PontosRanking pr WHERE pr.expiraEm IS NOT NULL AND pr.expiraEm < :dataExpiracao AND pr.pontosAtivos > 0")
    List<PontosRanking> findPontosExpirados(@Param("dataExpiracao") LocalDateTime dataExpiracao);

    @Query("SELECT SUM(pr.pontosAtivos) FROM PontosRanking pr WHERE pr.freteiro.id = :freteiroId")
    Integer getTotalPontosAtivosByFreteiro(@Param("freteiroId") Long freteiroId);

    @Query("SELECT COUNT(pr) FROM PontosRanking pr WHERE pr.cidade = :cidade AND pr.pontosAtivos > :pontos")
    Long countFreteiroComMaisPontos(@Param("cidade") String cidade, @Param("pontos") Integer pontos);

    boolean existsByFreteiroAndCidade(Freteiro freteiro, String cidade);
}
