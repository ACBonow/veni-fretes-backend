package com.venifretes.controller;

import com.venifretes.dto.response.DashboardResponse;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.Usuario;
import com.venifretes.repository.UsuarioRepository;
import com.venifretes.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/freteiro/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Freteiro", description = "Dashboard com métricas do freteiro")
@SecurityRequirement(name = "bearer-jwt")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Obter métricas do dashboard",
            description = "Retorna métricas de visualizações, cliques, avaliações e taxa de conversão")
    public ResponseEntity<DashboardResponse> getDashboard() {
        Freteiro freteiro = getAuthenticatedFreteiro();
        DashboardResponse dashboard = dashboardService.obterMetricas(freteiro.getId());
        return ResponseEntity.ok(dashboard);
    }

    private Freteiro getAuthenticatedFreteiro() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!(usuario instanceof Freteiro)) {
            throw new RuntimeException("Usuário não é um freteiro");
        }

        return (Freteiro) usuario;
    }
}
