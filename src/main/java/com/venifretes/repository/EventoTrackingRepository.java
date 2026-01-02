package com.venifretes.repository;

import com.venifretes.model.entity.EventoTracking;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.enums.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoTrackingRepository extends JpaRepository<EventoTracking, Long> {

    long countByFreteiroAndTipoAndCreatedAtBetween(
        Freteiro freteiro,
        TipoEvento tipo,
        LocalDateTime inicio,
        LocalDateTime fim
    );

    @Query("""
        SELECT e.tipo as tipo, COUNT(e) as quantidade
        FROM EventoTracking e
        WHERE e.freteiro = :freteiro
        AND e.createdAt BETWEEN :inicio AND :fim
        GROUP BY e.tipo
    """)
    List<Object[]> contarEventosPorTipo(
        @Param("freteiro") Freteiro freteiro,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    List<EventoTracking> findByNotificacaoEnviadaFalseAndTipoIn(List<TipoEvento> tipos);

    long countByFreteiroAndTipoAndCreatedAtAfter(
        Freteiro freteiro,
        TipoEvento tipo,
        LocalDateTime dataInicio
    );

    long countByFreteiroAndCreatedAtAfterAndTipoIn(
        Freteiro freteiro,
        LocalDateTime dataInicio,
        List<TipoEvento> tipos
    );

    // Admin statistics queries
    long countByCreatedAtAfter(LocalDateTime dataInicio);
    long countByTipoAndCreatedAtAfter(TipoEvento tipo, LocalDateTime dataInicio);
    long countByTipoInAndCreatedAtAfter(List<TipoEvento> tipos, LocalDateTime dataInicio);

    @Query("SELECT COUNT(e) FROM EventoTracking e WHERE e.tipo = :tipo")
    long countByTipo(@Param("tipo") TipoEvento tipo);
}
