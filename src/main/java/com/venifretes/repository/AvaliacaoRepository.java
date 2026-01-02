package com.venifretes.repository;

import com.venifretes.model.entity.Avaliacao;
import com.venifretes.model.entity.Freteiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Page<Avaliacao> findByFreteiroAndAprovadoTrueOrderByCreatedAtDesc(
        Freteiro freteiro,
        Pageable pageable
    );

    long countByFreteiroAndAprovadoTrue(Freteiro freteiro);

    @Query("""
        SELECT AVG(a.nota) FROM Avaliacao a
        WHERE a.freteiro = :freteiro
        AND a.aprovado = true
    """)
    BigDecimal calcularMediaAvaliacoes(@Param("freteiro") Freteiro freteiro);

    @Query("""
        SELECT a.nota as nota, COUNT(a) as quantidade
        FROM Avaliacao a
        WHERE a.freteiro = :freteiro
        AND a.aprovado = true
        GROUP BY a.nota
    """)
    List<Map<String, Object>> contarPorNota(@Param("freteiro") Freteiro freteiro);

    // Admin statistics queries
    long countByAprovadoTrue();
    long countByAprovadoFalse();

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.aprovado = true")
    BigDecimal calcularMediaGeralAvaliacoes();

    @Query("""
        SELECT a.nota as nota, COUNT(a) as quantidade
        FROM Avaliacao a
        WHERE a.aprovado = true
        GROUP BY a.nota
    """)
    List<Object[]> contarPorNotaGeral();

    // Admin review management
    Page<Avaliacao> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Avaliacao> findByAprovado(Boolean aprovado, Pageable pageable);
}
