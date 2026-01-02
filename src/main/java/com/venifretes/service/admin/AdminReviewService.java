package com.venifretes.service.admin;

import com.venifretes.dto.response.ReviewListResponse;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Avaliacao;
import com.venifretes.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReviewService {

    private final AvaliacaoRepository avaliacaoRepository;

    /**
     * Lista todas as avaliações com paginação
     */
    @Transactional(readOnly = true)
    public Page<ReviewListResponse> listAllReviews(Pageable pageable) {
        return avaliacaoRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::toReviewListResponse);
    }

    /**
     * Lista avaliações filtradas por status de aprovação
     */
    @Transactional(readOnly = true)
    public Page<ReviewListResponse> listReviewsByStatus(Boolean aprovado, Pageable pageable) {
        return avaliacaoRepository.findByAprovado(aprovado, pageable)
                .map(this::toReviewListResponse);
    }

    /**
     * Busca uma avaliação por ID
     */
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewById(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));
        return toReviewListResponse(avaliacao);
    }

    /**
     * Aprova uma avaliação
     */
    @Transactional
    public ReviewListResponse approveReview(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setAprovado(true);
        Avaliacao saved = avaliacaoRepository.save(avaliacao);

        log.info("Review approved: id={}, freteiroId={}", id, avaliacao.getFreteiro().getId());

        return toReviewListResponse(saved);
    }

    /**
     * Rejeita/despublica uma avaliação
     */
    @Transactional
    public ReviewListResponse rejectReview(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setAprovado(false);
        Avaliacao saved = avaliacaoRepository.save(avaliacao);

        log.info("Review rejected: id={}, freteiroId={}", id, avaliacao.getFreteiro().getId());

        return toReviewListResponse(saved);
    }

    /**
     * Deleta uma avaliação permanentemente
     */
    @Transactional
    public void deleteReview(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacaoRepository.delete(avaliacao);

        log.info("Review deleted: id={}, freteiroId={}", id, avaliacao.getFreteiro().getId());
    }

    /**
     * Converte Avaliacao para ReviewListResponse
     */
    private ReviewListResponse toReviewListResponse(Avaliacao avaliacao) {
        String contratanteNome = avaliacao.getUsuario() != null
                ? avaliacao.getUsuario().getNome()
                : avaliacao.getNomeAvaliador();

        return ReviewListResponse.builder()
                .id(avaliacao.getId())
                .freteiroNome(avaliacao.getFreteiro().getNome())
                .freteiroSlug(avaliacao.getFreteiro().getSlug())
                .contratanteNome(contratanteNome)
                .nota(avaliacao.getNota())
                .comentario(avaliacao.getComentario())
                .aprovado(avaliacao.getAprovado())
                .createdAt(avaliacao.getCreatedAt())
                .build();
    }
}
