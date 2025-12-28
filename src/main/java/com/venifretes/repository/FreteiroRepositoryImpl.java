package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FreteiroRepositoryImpl implements FreteiroRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Page<Freteiro> findFreteirosRanqueadosCustom(String cidade, String estado, BigDecimal avaliacaoMinima, Pageable pageable) {
        String sql = """
            SELECT f.*, p.nome, p.telefone, p.email, p.created_at, p.updated_at,
                   u.password, u.role, u.email_verificado, u.last_login_at, u.ativo
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
                AND (CAST(:avaliacaoMinima AS DECIMAL) IS NULL OR f.avaliacao_media >= CAST(:avaliacaoMinima AS DECIMAL))
            GROUP BY f.usuario_id, f.slug, f.cidade, f.estado, f.areas_atendidas, f.descricao,
                     f.foto_perfil, f.fotos_veiculo, f.porcentagem_completude, f.avaliacao_media,
                     f.total_avaliacoes, f.total_visualizacoes, f.total_cliques, f.cliques_whatsapp,
                     f.cliques_telefone, f.verificado, f.data_verificacao,
                     p.id, p.nome, p.telefone, p.email, p.created_at, p.updated_at,
                     u.password, u.role, u.email_verificado, u.last_login_at, u.ativo
            ORDER BY
                CASE WHEN MAX(a.plano_id) = 'MASTER' THEN 4
                     WHEN MAX(a.plano_id) = 'PREMIUM' THEN 3
                     WHEN MAX(a.plano_id) = 'PADRAO' THEN 2
                     ELSE 1 END DESC,
                COALESCE(MAX(pr.pontos_ativos), 0) DESC,
                f.porcentagem_completude DESC,
                f.avaliacao_media DESC,
                f.usuario_id ASC
            LIMIT :limit OFFSET :offset
            """;

        String countSql = """
            SELECT COUNT(DISTINCT f.usuario_id)
            FROM freteiros f
            JOIN usuarios u ON f.usuario_id = u.pessoa_id
            WHERE u.ativo = true
                AND (CAST(:cidade AS VARCHAR) IS NULL OR LOWER(f.cidade) = LOWER(CAST(:cidade AS VARCHAR)))
                AND (CAST(:estado AS VARCHAR) IS NULL OR UPPER(f.estado) = UPPER(CAST(:estado AS VARCHAR)))
                AND (CAST(:avaliacaoMinima AS DECIMAL) IS NULL OR f.avaliacao_media >= CAST(:avaliacaoMinima AS DECIMAL))
            """;

        Query query = entityManager.createNativeQuery(sql, Freteiro.class);
        query.setParameter("cidade", cidade);
        query.setParameter("estado", estado);
        query.setParameter("avaliacaoMinima", avaliacaoMinima);
        query.setParameter("limit", pageable.getPageSize());
        query.setParameter("offset", pageable.getOffset());

        List<Freteiro> freteiros = query.getResultList();

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("cidade", cidade);
        countQuery.setParameter("estado", estado);
        countQuery.setParameter("avaliacaoMinima", avaliacaoMinima);

        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(freteiros, pageable, total);
    }
}
