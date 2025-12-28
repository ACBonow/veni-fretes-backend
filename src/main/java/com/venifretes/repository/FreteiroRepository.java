package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface FreteiroRepository extends JpaRepository<Freteiro, Long>,
                                            JpaSpecificationExecutor<Freteiro>,
                                            FreteiroRepositoryCustom {

    Optional<Freteiro> findBySlug(String slug);

    Optional<Freteiro> findByEmail(String email);

    boolean existsBySlug(String slug);

    /**
     * Query principal de ranqueamento com filtros
     * Ordenação: Plano > Pontos Extras > Completude > Avaliação > Rotação (ID)
     */
    @Query(value = """
        SELECT f.*, p.nome, p.telefone, p.email, p.created_at, p.updated_at,
               u.password, u.role, u.email_verificado, u.last_login_at, u.ativo,
               CASE WHEN MAX(a.plano_id) = 'MASTER' THEN 4
                    WHEN MAX(a.plano_id) = 'PREMIUM' THEN 3
                    WHEN MAX(a.plano_id) = 'PADRAO' THEN 2
                    ELSE 1 END as plano_ordem,
               COALESCE(MAX(pr.pontos_ativos), 0) as pontos_ranking
        FROM freteiros f
        JOIN usuarios u ON f.usuario_id = u.pessoa_id
        JOIN pessoas p ON u.pessoa_id = p.id
        LEFT JOIN assinaturas a ON f.usuario_id = a.freteiro_id
            AND (a.status = 'ATIVA' OR a.status = 'EM_PERIODO_TESTE')
        LEFT JOIN pontos_ranking pr ON pr.freteiro_id = f.usuario_id
            AND (CAST(:cidade AS VARCHAR) IS NULL OR LOWER(pr.cidade) = LOWER(CAST(:cidade AS VARCHAR)))
            AND pr.ativo = true
            AND (pr.expira_em IS NULL OR pr.expira_em > CURRENT_TIMESTAMP)
        WHERE u.ativo = true
            AND (CAST(:cidade AS VARCHAR) IS NULL OR LOWER(f.cidade) = LOWER(CAST(:cidade AS VARCHAR)))
            AND (CAST(:estado AS VARCHAR) IS NULL OR UPPER(f.estado) = UPPER(CAST(:estado AS VARCHAR)))
            AND (:avaliacaoMinima IS NULL OR f.avaliacao_media >= :avaliacaoMinima)
        GROUP BY f.usuario_id, f.slug, f.cidade, f.estado, f.areas_atendidas, f.descricao,
                 f.foto_perfil, f.fotos_veiculo, f.porcentagem_completude, f.avaliacao_media,
                 f.total_avaliacoes, f.total_visualizacoes, f.total_cliques, f.cliques_whatsapp,
                 f.cliques_telefone, f.verificado, f.data_verificacao,
                 p.id, p.nome, p.telefone, p.email, p.created_at, p.updated_at,
                 u.password, u.role, u.email_verificado, u.last_login_at, u.ativo
        ORDER BY plano_ordem DESC,
                 pontos_ranking DESC,
                 f.porcentagem_completude DESC,
                 f.avaliacao_media DESC,
                 f.usuario_id ASC
    """,
    countQuery = "SELECT COUNT(DISTINCT f.usuario_id) FROM freteiros f JOIN usuarios u ON f.usuario_id = u.pessoa_id WHERE u.ativo = true",
    nativeQuery = true)
    Page<Freteiro> findFreteirosRanqueados(
        @Param("cidade") String cidade,
        @Param("estado") String estado,
        @Param("avaliacaoMinima") BigDecimal avaliacaoMinima,
        Pageable pageable
    );

    @Query("""
        SELECT COUNT(DISTINCT f) FROM Freteiro f
        WHERE f.cidade = :cidade
        AND f.estado = :estado
        AND f.ativo = true
    """)
    long countByCidadeAndEstado(
        @Param("cidade") String cidade,
        @Param("estado") String estado
    );

    List<Freteiro> findTop10ByOrderByAvaliacaoMediaDescTotalAvaliacoesDesc();

    long countByVerificadoTrue();

    long countByAtivoTrue();
}
