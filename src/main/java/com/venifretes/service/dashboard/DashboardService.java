package com.venifretes.service.dashboard;

import com.venifretes.dto.response.DashboardResponse;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.enums.TipoEvento;
import com.venifretes.repository.EventoTrackingRepository;
import com.venifretes.repository.FreteiroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final FreteiroRepository freteiroRepository;
    private final EventoTrackingRepository eventoTrackingRepository;

    @Transactional(readOnly = true)
    public DashboardResponse obterMetricas(Long freteiroId) {
        Freteiro freteiro = freteiroRepository.findById(freteiroId)
                .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        LocalDateTime dataInicio30Dias = LocalDateTime.now().minusDays(30);

        // Visualizações últimos 30 dias
        long visualizacoes30Dias = eventoTrackingRepository.countByFreteiroAndTipoAndCreatedAtAfter(
                freteiro, TipoEvento.VISUALIZACAO_PERFIL, dataInicio30Dias
        );

        // Cliques últimos 30 dias
        long cliques30Dias = eventoTrackingRepository.countByFreteiroAndCreatedAtAfterAndTipoIn(
                freteiro,
                dataInicio30Dias,
                java.util.List.of(TipoEvento.CLIQUE_WHATSAPP, TipoEvento.CLIQUE_TELEFONE)
        );

        // Calcular taxa de conversão
        double taxaConversao = 0.0;
        if (freteiro.getTotalVisualizacoes() > 0) {
            taxaConversao = ((double) freteiro.getTotalCliques() / freteiro.getTotalVisualizacoes()) * 100;
        }

        return DashboardResponse.builder()
                .totalVisualizacoes(freteiro.getTotalVisualizacoes())
                .visualizacoesUltimos30Dias(visualizacoes30Dias)
                .totalCliques(freteiro.getTotalCliques())
                .cliquesWhatsApp(freteiro.getCliquesWhatsApp())
                .cliquesTelefone(freteiro.getCliquesTelefone())
                .cliquesUltimos30Dias(cliques30Dias)
                .avaliacaoMedia(freteiro.getAvaliacaoMedia())
                .totalAvaliacoes(freteiro.getTotalAvaliacoes())
                .porcentagemCompletude(freteiro.getPorcentagemCompletude())
                .verificado(freteiro.getVerificado())
                .ativo(freteiro.getAtivo())
                .taxaConversao(Math.round(taxaConversao * 100.0) / 100.0)
                .build();
    }
}
