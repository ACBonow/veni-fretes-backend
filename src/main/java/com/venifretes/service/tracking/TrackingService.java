package com.venifretes.service.tracking;

import com.venifretes.model.entity.EventoTracking;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.enums.TipoEvento;
import com.venifretes.repository.EventoTrackingRepository;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.service.notification.WhatsAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

    private final EventoTrackingRepository eventoRepository;
    private final FreteiroRepository freteiroRepository;
    private final WhatsAppService whatsAppService;

    @Transactional
    public void registrarEvento(Long freteiroId, TipoEvento tipo, String ip, String userAgent, String origem, String referer) {
        Freteiro freteiro = freteiroRepository.findById(freteiroId)
            .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        EventoTracking evento = EventoTracking.builder()
            .freteiro(freteiro)
            .tipo(tipo)
            .ip(ip)
            .userAgent(userAgent)
            .origem(origem)
            .referer(referer)
            .build();

        eventoRepository.save(evento);

        // Atualizar contadores no freteiro
        atualizarContadores(freteiro, tipo);

        // Enviar notificação assíncrona se for clique
        if (tipo == TipoEvento.CLIQUE_WHATSAPP || tipo == TipoEvento.CLIQUE_TELEFONE) {
            enviarNotificacaoAsync(freteiro, evento);
        }

        log.info("Evento {} registrado para freteiro {}", tipo, freteiro.getId());
    }

    private void atualizarContadores(Freteiro freteiro, TipoEvento tipo) {
        switch (tipo) {
            case VISUALIZACAO_PERFIL -> {
                Long totalVisualizacoes = freteiro.getTotalVisualizacoes();
                freteiro.setTotalVisualizacoes((totalVisualizacoes != null ? totalVisualizacoes : 0L) + 1);
            }
            case CLIQUE_WHATSAPP -> {
                Long cliquesWhatsApp = freteiro.getCliquesWhatsApp();
                Long totalCliques = freteiro.getTotalCliques();
                freteiro.setCliquesWhatsApp((cliquesWhatsApp != null ? cliquesWhatsApp : 0L) + 1);
                freteiro.setTotalCliques((totalCliques != null ? totalCliques : 0L) + 1);
            }
            case CLIQUE_TELEFONE -> {
                Long cliquesTelefone = freteiro.getCliquesTelefone();
                Long totalCliques = freteiro.getTotalCliques();
                freteiro.setCliquesTelefone((cliquesTelefone != null ? cliquesTelefone : 0L) + 1);
                freteiro.setTotalCliques((totalCliques != null ? totalCliques : 0L) + 1);
            }
            case CLIQUE_CARD, CLIQUE_LINK_EXTERNO -> {
                Long totalCliques = freteiro.getTotalCliques();
                freteiro.setTotalCliques((totalCliques != null ? totalCliques : 0L) + 1);
            }
        }
        freteiroRepository.save(freteiro);
    }

    @Async
    public void enviarNotificacaoAsync(Freteiro freteiro, EventoTracking evento) {
        try {
            String tipoContato = evento.getTipo() == TipoEvento.CLIQUE_WHATSAPP ? "WhatsApp" : "Telefone";
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            String mensagem = String.format(
                "🔔 Olá %s!\n\nVocê recebeu um novo contato via %s às %s.\n\nAcesse seu dashboard: https://venifretes.com.br/freteiro/dashboard",
                freteiro.getNome(),
                tipoContato,
                dataHora
            );

            whatsAppService.enviarNotificacao(freteiro.getTelefone(), mensagem);

            evento.setNotificacaoEnviada(true);
            evento.setDataNotificacao(LocalDateTime.now());
            eventoRepository.save(evento);

        } catch (Exception e) {
            log.error("Erro ao enviar notificação para freteiro {}", freteiro.getId(), e);
        }
    }
}
