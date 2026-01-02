package com.venifretes.controller;

import com.venifretes.dto.response.*;
import com.venifretes.service.admin.AdminStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
@RequiredArgsConstructor
@Tag(name = "Admin Statistics", description = "Endpoints de estatísticas administrativas")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatsController {

    private final AdminStatsService adminStatsService;

    @GetMapping("/users")
    @Operation(
            summary = "Estatísticas de usuários",
            description = "Retorna estatísticas gerais sobre usuários cadastrados no sistema"
    )
    public ResponseEntity<UserStatsResponse> getUserStats() {
        UserStatsResponse stats = adminStatsService.getUserStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/engagement")
    @Operation(
            summary = "Métricas de engajamento",
            description = "Retorna métricas de visualizações, cliques e taxa de conversão"
    )
    public ResponseEntity<EngagementStatsResponse> getEngagementStats() {
        EngagementStatsResponse stats = adminStatsService.getEngagementStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/financial")
    @Operation(
            summary = "Métricas financeiras",
            description = "Retorna receita de assinaturas, vendas de pontos e totais financeiros"
    )
    public ResponseEntity<FinancialStatsResponse> getFinancialStats() {
        FinancialStatsResponse stats = adminStatsService.getFinancialStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/growth")
    @Operation(
            summary = "Métricas de crescimento",
            description = "Retorna estatísticas de crescimento e novos cadastros por período"
    )
    public ResponseEntity<GrowthStatsResponse> getGrowthStats() {
        GrowthStatsResponse stats = adminStatsService.getGrowthStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/reviews")
    @Operation(
            summary = "Estatísticas de avaliações",
            description = "Retorna total de avaliações, aprovadas, pendentes e distribuição por nota"
    )
    public ResponseEntity<ReviewStatsResponse> getReviewStats() {
        ReviewStatsResponse stats = adminStatsService.getReviewStats();
        return ResponseEntity.ok(stats);
    }
}
