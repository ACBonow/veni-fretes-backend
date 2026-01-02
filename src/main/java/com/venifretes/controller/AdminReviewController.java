package com.venifretes.controller;

import com.venifretes.dto.response.ReviewListResponse;
import com.venifretes.service.admin.AdminReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
@Tag(name = "Admin Review Management", description = "Endpoints de gerenciamento de avaliações (Admin)")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping
    @Operation(
            summary = "Listar todas as avaliações",
            description = "Lista todas as avaliações com paginação e filtro opcional por status"
    )
    public ResponseEntity<Page<ReviewListResponse>> listReviews(
            @Parameter(description = "Filtrar por status (true=aprovadas, false=pendentes, null=todas)")
            @RequestParam(required = false) Boolean aprovado,
            @Parameter(description = "Número da página (inicia em 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo para ordenação")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação")
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ReviewListResponse> reviews;
        if (aprovado != null) {
            reviews = adminReviewService.listReviewsByStatus(aprovado, pageable);
        } else {
            reviews = adminReviewService.listAllReviews(pageable);
        }

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar avaliação por ID",
            description = "Retorna os detalhes de uma avaliação específica"
    )
    public ResponseEntity<ReviewListResponse> getReview(@PathVariable Long id) {
        ReviewListResponse review = adminReviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}/approve")
    @Operation(
            summary = "Aprovar avaliação",
            description = "Marca uma avaliação como aprovada e a publica no perfil do freteiro"
    )
    public ResponseEntity<ReviewListResponse> approveReview(@PathVariable Long id) {
        ReviewListResponse review = adminReviewService.approveReview(id);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{id}/reject")
    @Operation(
            summary = "Rejeitar avaliação",
            description = "Marca uma avaliação como rejeitada e a remove da publicação"
    )
    public ResponseEntity<ReviewListResponse> rejectReview(@PathVariable Long id) {
        ReviewListResponse review = adminReviewService.rejectReview(id);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar avaliação",
            description = "Remove permanentemente uma avaliação do sistema"
    )
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        adminReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
