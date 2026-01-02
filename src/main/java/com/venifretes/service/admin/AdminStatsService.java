package com.venifretes.service.admin;

import com.venifretes.dto.response.*;
import com.venifretes.model.enums.Role;
import com.venifretes.model.enums.StatusAssinatura;
import com.venifretes.model.enums.TipoEvento;
import com.venifretes.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminStatsService {

    private final UsuarioRepository usuarioRepository;
    private final FreteiroRepository freteiroRepository;
    private final EventoTrackingRepository eventoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final AssinaturaRepository assinaturaRepository;
    private final HistoricoCompraPontosRepository historicoCompraPontosRepository;

    /**
     * Obtém estatísticas de usuários
     */
    @Transactional(readOnly = true)
    public UserStatsResponse getUserStats() {
        long totalUsuarios = usuarioRepository.count();
        long totalFreteiros = usuarioRepository.countByRole(Role.FRETEIRO);
        long totalContratantes = usuarioRepository.countByRole(Role.CONTRATANTE);
        long totalAdmins = usuarioRepository.countByRole(Role.ADMIN);
        long usuariosAtivos = usuarioRepository.countByAtivoTrue();
        long usuariosInativos = usuarioRepository.countByAtivoFalse();
        long freteirosVerificados = freteiroRepository.countByVerificadoTrue();
        long freteirosAtivos = freteiroRepository.countByAtivoTrue();

        return UserStatsResponse.builder()
                .totalUsuarios(totalUsuarios)
                .totalFreteiros(totalFreteiros)
                .totalContratantes(totalContratantes)
                .totalAdmins(totalAdmins)
                .usuariosAtivos(usuariosAtivos)
                .usuariosInativos(usuariosInativos)
                .freteirosVerificados(freteirosVerificados)
                .freteirosAtivos(freteirosAtivos)
                .build();
    }

    /**
     * Obtém métricas de engajamento
     */
    @Transactional(readOnly = true)
    public EngagementStatsResponse getEngagementStats() {
        LocalDateTime ultimos30Dias = LocalDateTime.now().minusDays(30);

        // Total de visualizações
        long totalVisualizacoes = eventoRepository.countByTipo(TipoEvento.VISUALIZACAO_PERFIL);
        long visualizacoesUltimos30Dias = eventoRepository.countByTipoAndCreatedAtAfter(
                TipoEvento.VISUALIZACAO_PERFIL,
                ultimos30Dias
        );

        // Cliques por tipo
        long cliquesWhatsApp = eventoRepository.countByTipo(TipoEvento.CLIQUE_WHATSAPP);
        long cliquesTelefone = eventoRepository.countByTipo(TipoEvento.CLIQUE_TELEFONE);

        // Total de cliques (todos os tipos de clique)
        List<TipoEvento> tiposClique = Arrays.asList(
                TipoEvento.CLIQUE_WHATSAPP,
                TipoEvento.CLIQUE_TELEFONE,
                TipoEvento.CLIQUE_CARD,
                TipoEvento.CLIQUE_LINK_EXTERNO
        );
        long totalCliques = eventoRepository.countByTipoInAndCreatedAtAfter(tiposClique, LocalDateTime.MIN);
        long cliquesUltimos30Dias = eventoRepository.countByTipoInAndCreatedAtAfter(tiposClique, ultimos30Dias);

        // Taxa de conversão (cliques / visualizações)
        double taxaConversao = totalVisualizacoes > 0
                ? (double) totalCliques / totalVisualizacoes
                : 0.0;

        return EngagementStatsResponse.builder()
                .totalVisualizacoes(totalVisualizacoes)
                .visualizacoesUltimos30Dias(visualizacoesUltimos30Dias)
                .totalCliques(totalCliques)
                .cliquesUltimos30Dias(cliquesUltimos30Dias)
                .cliquesWhatsApp(cliquesWhatsApp)
                .cliquesTelefone(cliquesTelefone)
                .taxaConversaoGeral(Math.round(taxaConversao * 1000.0) / 1000.0) // 3 decimais
                .build();
    }

    /**
     * Obtém métricas financeiras
     */
    @Transactional(readOnly = true)
    public FinancialStatsResponse getFinancialStats() {
        LocalDateTime ultimos30Dias = LocalDateTime.now().minusDays(30);

        // Receita de assinaturas
        BigDecimal receitaTotalAssinaturas = assinaturaRepository.getTotalReceitaAssinaturas();
        BigDecimal receitaUltimos30Dias = assinaturaRepository.getReceitaPeriodo(ultimos30Dias);
        long assinaturasAtivas = assinaturaRepository.countByStatus(StatusAssinatura.ATIVA)
                + assinaturaRepository.countByStatus(StatusAssinatura.EM_PERIODO_TESTE);

        // Receita de pontos
        BigDecimal receitaPontos = historicoCompraPontosRepository.getTotalReceitaPontos();
        Integer totalPontosVendidos = historicoCompraPontosRepository.getTotalPontosVendidos();

        return FinancialStatsResponse.builder()
                .receitaTotalAssinaturas(receitaTotalAssinaturas != null ? receitaTotalAssinaturas : BigDecimal.ZERO)
                .receitaUltimos30Dias(receitaUltimos30Dias != null ? receitaUltimos30Dias : BigDecimal.ZERO)
                .assinaturasAtivas(assinaturasAtivas)
                .receitaPontos(receitaPontos != null ? receitaPontos : BigDecimal.ZERO)
                .totalPontosVendidos(totalPontosVendidos != null ? totalPontosVendidos : 0)
                .build();
    }

    /**
     * Obtém métricas de crescimento
     */
    @Transactional(readOnly = true)
    public GrowthStatsResponse getGrowthStats() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime ultimos7Dias = agora.minusDays(7);
        LocalDateTime ultimos30Dias = agora.minusDays(30);
        LocalDateTime ultimos60Dias = agora.minusDays(60);
        LocalDateTime ultimos90Dias = agora.minusDays(90);

        // Contagens por período
        long cadastrosUltimos7Dias = usuarioRepository.countByCreatedAtAfter(ultimos7Dias);
        long cadastrosUltimos30Dias = usuarioRepository.countByCreatedAtAfter(ultimos30Dias);
        long cadastrosUltimos90Dias = usuarioRepository.countByCreatedAtAfter(ultimos90Dias);

        // Cadastros dos últimos 30-60 dias (período anterior para calcular taxa de crescimento)
        long cadastrosPeriodoAnterior = usuarioRepository.countByCreatedAtBetween(ultimos60Dias, ultimos30Dias);

        // Taxa de crescimento mensal
        double taxaCrescimento = cadastrosPeriodoAnterior > 0
                ? ((double) cadastrosUltimos30Dias - cadastrosPeriodoAnterior) / cadastrosPeriodoAnterior * 100
                : 0.0;

        // Cadastros por dia (simplified - in a real implementation you'd query the database)
        // For now, returning null to avoid complex native query
        List<GrowthStatsResponse.DailyCounts> cadastrosPorDia = null;

        return GrowthStatsResponse.builder()
                .cadastrosUltimos7Dias(cadastrosUltimos7Dias)
                .cadastrosUltimos30Dias(cadastrosUltimos30Dias)
                .cadastrosUltimos90Dias(cadastrosUltimos90Dias)
                .cadastrosPorDia(cadastrosPorDia)
                .taxaCrescimentoMensal(Math.round(taxaCrescimento * 100.0) / 100.0)
                .build();
    }

    /**
     * Obtém estatísticas de avaliações
     */
    @Transactional(readOnly = true)
    public ReviewStatsResponse getReviewStats() {
        long totalAvaliacoes = avaliacaoRepository.count();
        long avaliacoesAprovadas = avaliacaoRepository.countByAprovadoTrue();
        long avaliacoesPendentes = avaliacaoRepository.countByAprovadoFalse();

        BigDecimal notaMediaGeral = avaliacaoRepository.calcularMediaGeralAvaliacoes();
        if (notaMediaGeral != null) {
            notaMediaGeral = notaMediaGeral.setScale(2, RoundingMode.HALF_UP);
        } else {
            notaMediaGeral = BigDecimal.ZERO;
        }

        // Distribuição por nota
        Map<Integer, Long> distribuicaoPorNota = new HashMap<>();
        List<Object[]> distribuicao = avaliacaoRepository.contarPorNotaGeral();
        for (Object[] row : distribuicao) {
            Integer nota = (Integer) row[0];
            Long quantidade = (Long) row[1];
            distribuicaoPorNota.put(nota, quantidade);
        }

        // Garantir que todas as notas de 1-5 estejam presentes
        for (int i = 1; i <= 5; i++) {
            distribuicaoPorNota.putIfAbsent(i, 0L);
        }

        return ReviewStatsResponse.builder()
                .totalAvaliacoes(totalAvaliacoes)
                .avaliacoesAprovadas(avaliacoesAprovadas)
                .avaliacoesPendentes(avaliacoesPendentes)
                .notaMediaGeral(notaMediaGeral)
                .distribuicaoPorNota(distribuicaoPorNota)
                .build();
    }
}
