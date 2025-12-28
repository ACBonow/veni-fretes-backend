package com.venifretes.controller;

import com.venifretes.dto.pontos.*;
import com.venifretes.model.entity.Usuario;
import com.venifretes.repository.UsuarioRepository;
import com.venifretes.service.pontos.PontosRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freteiro/pontos")
@RequiredArgsConstructor
@Tag(name = "Pontos Ranking", description = "Gerenciamento de pontos extras para ranking")
@SecurityRequirement(name = "bearer-jwt")
public class PontosRankingController {

    private final PontosRankingService pontosRankingService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/simular")
    @Operation(summary = "Simular compra de pontos",
            description = "Simula a compra de pontos e mostra a posição estimada no ranking")
    public ResponseEntity<SimulacaoPontosResponse> simularCompra(
            @RequestParam @NotBlank String cidade,
            @RequestParam @Min(10) Integer pontos) {

        Usuario usuario = getAuthenticatedUser();
        SimulacaoPontosResponse response = pontosRankingService.simularCompraPontos(
                usuario.getId(), cidade, pontos
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/comprar")
    @Operation(summary = "Comprar pontos",
            description = "Realiza a compra de pontos extras para melhorar posição no ranking")
    public ResponseEntity<CompraPontosResponse> comprarPontos(
            @Valid @RequestBody CompraPontosRequest request) {

        Usuario usuario = getAuthenticatedUser();
        CompraPontosResponse response = pontosRankingService.comprarPontos(usuario.getId(), request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(summary = "Consultar status dos pontos",
            description = "Retorna informações sobre pontos ativos, gastos e histórico recente")
    public ResponseEntity<PontosStatusResponse> consultarStatus(
            @RequestParam @NotBlank String cidade) {

        Usuario usuario = getAuthenticatedUser();
        PontosStatusResponse response = pontosRankingService.consultarStatus(usuario.getId(), cidade);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/historico")
    @Operation(summary = "Histórico de compras",
            description = "Lista o histórico completo de compras de pontos")
    public ResponseEntity<Page<HistoricoCompraResponse>> buscarHistorico(
            @RequestParam(required = false) String cidade,
            @PageableDefault(size = 20) Pageable pageable) {

        Usuario usuario = getAuthenticatedUser();
        Page<HistoricoCompraResponse> response = pontosRankingService.buscarHistorico(
                usuario.getId(), cidade, pageable
        );

        return ResponseEntity.ok(response);
    }

    private Usuario getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
