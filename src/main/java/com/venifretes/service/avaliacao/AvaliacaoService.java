package com.venifretes.service.avaliacao;

import com.venifretes.dto.request.AvaliacaoRequest;
import com.venifretes.dto.response.AvaliacaoResponse;
import com.venifretes.dto.response.AvaliacaoStatsResponse;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Avaliacao;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.repository.AvaliacaoRepository;
import com.venifretes.repository.FreteiroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final FreteiroRepository freteiroRepository;

    @Transactional(readOnly = true)
    public Page<AvaliacaoResponse> listarAvaliacoes(Long freteiroId, Pageable pageable) {
        Freteiro freteiro = freteiroRepository.findById(freteiroId)
                .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));

        Page<Avaliacao> avaliacoes = avaliacaoRepository
                .findByFreteiroAndAprovadoTrueOrderByCreatedAtDesc(freteiro, pageable);

        return avaliacoes.map(this::toResponse);
    }

    @Transactional
    public AvaliacaoResponse criarAvaliacao(Long freteiroId, AvaliacaoRequest request) {
        Freteiro freteiro = freteiroRepository.findById(freteiroId)
                .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));

        Avaliacao avaliacao = Avaliacao.builder()
                .freteiro(freteiro)
                .nota(request.getNota())
                .nomeAvaliador(request.getNomeAvaliador())
                .comentario(request.getComentario())
                .aprovado(false) // Precisa ser aprovada por moderação
                .build();

        Avaliacao saved = avaliacaoRepository.save(avaliacao);

        // Atualizar estatísticas do freteiro
        atualizarEstatisticasFreteiro(freteiro);

        log.info("Avaliação criada para freteiro {}: {} estrelas", freteiroId, request.getNota());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AvaliacaoStatsResponse obterEstatisticas(Long freteiroId) {
        Freteiro freteiro = freteiroRepository.findById(freteiroId)
                .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));

        BigDecimal media = avaliacaoRepository.calcularMediaAvaliacoes(freteiro);
        long total = avaliacaoRepository.countByFreteiroAndAprovadoTrue(freteiro);

        // Distribuição por nota
        List<Map<String, Object>> distribuicaoList = avaliacaoRepository.contarPorNota(freteiro);
        Map<Integer, Long> distribuicao = new HashMap<>();

        for (Map<String, Object> item : distribuicaoList) {
            Integer nota = (Integer) item.get("nota");
            Long quantidade = ((Number) item.get("quantidade")).longValue();
            distribuicao.put(nota, quantidade);
        }

        // Preencher notas que não têm avaliações com 0
        for (int i = 1; i <= 5; i++) {
            distribuicao.putIfAbsent(i, 0L);
        }

        return AvaliacaoStatsResponse.builder()
                .mediaGeral(media != null ? media : BigDecimal.ZERO)
                .totalAvaliacoes(total)
                .distribuicaoPorNota(distribuicao)
                .build();
    }

    private void atualizarEstatisticasFreteiro(Freteiro freteiro) {
        BigDecimal media = avaliacaoRepository.calcularMediaAvaliacoes(freteiro);
        long total = avaliacaoRepository.countByFreteiroAndAprovadoTrue(freteiro);

        freteiro.setAvaliacaoMedia(media != null ? media : BigDecimal.ZERO);
        freteiro.setTotalAvaliacoes((int) total);

        freteiroRepository.save(freteiro);
    }

    private AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        return AvaliacaoResponse.builder()
                .id(avaliacao.getId())
                .nomeAvaliador(avaliacao.getNomeAvaliador())
                .nota(avaliacao.getNota())
                .comentario(avaliacao.getComentario())
                .respostaFreteiro(avaliacao.getRespostaFreteiro())
                .dataResposta(avaliacao.getDataResposta())
                .createdAt(avaliacao.getCreatedAt())
                .build();
    }
}
