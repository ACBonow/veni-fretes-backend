package com.venifretes.controller;

import com.venifretes.dto.request.PlanoRequest;
import com.venifretes.dto.response.PlanoResponse;
import com.venifretes.model.enums.PlanoTipo;
import com.venifretes.service.plano.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
@RequiredArgsConstructor
@Tag(name = "Planos", description = "Planos de assinatura disponíveis")
public class PlanosController {

    private final PlanoService planoService;

    @GetMapping
    @Operation(summary = "Listar todos os planos disponíveis",
            description = "Retorna todos os planos ativos ordenados por ordem de exibição")
    public ResponseEntity<List<PlanoResponse>> listarPlanos() {
        List<PlanoResponse> planos = planoService.listarPlanosAtivos();
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/{tipo}")
    @Operation(summary = "Buscar plano por tipo",
            description = "Retorna os detalhes de um plano específico")
    public ResponseEntity<PlanoResponse> buscarPorTipo(@PathVariable PlanoTipo tipo) {
        PlanoResponse plano = planoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(plano);
    }

    // ========== ENDPOINTS DE ADMINISTRAÇÃO ==========

    @GetMapping("/admin/todos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[ADMIN] Listar todos os planos",
            description = "Retorna todos os planos, incluindo inativos. Requer permissão de administrador.")
    public ResponseEntity<List<PlanoResponse>> listarTodosPlanos() {
        List<PlanoResponse> planos = planoService.listarTodosPlanos();
        return ResponseEntity.ok(planos);
    }

    @PostMapping("/admin/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[ADMIN] Criar novo plano",
            description = "Cria um novo plano de assinatura. Requer permissão de administrador.")
    public ResponseEntity<PlanoResponse> criarPlano(
            @PathVariable PlanoTipo tipo,
            @Valid @RequestBody PlanoRequest request) {
        PlanoResponse plano = planoService.criarPlano(tipo, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @PutMapping("/admin/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[ADMIN] Atualizar plano existente",
            description = "Atualiza as informações de um plano existente. Requer permissão de administrador.")
    public ResponseEntity<PlanoResponse> atualizarPlano(
            @PathVariable PlanoTipo tipo,
            @Valid @RequestBody PlanoRequest request) {
        PlanoResponse plano = planoService.atualizarPlano(tipo, request);
        return ResponseEntity.ok(plano);
    }

    @PatchMapping("/admin/{tipo}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[ADMIN] Alterar status do plano",
            description = "Ativa ou desativa um plano. Requer permissão de administrador.")
    public ResponseEntity<PlanoResponse> alterarStatusPlano(
            @PathVariable PlanoTipo tipo,
            @RequestParam boolean ativo) {
        PlanoResponse plano = planoService.alterarStatusPlano(tipo, ativo);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/admin/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[ADMIN] Deletar plano",
            description = "Deleta (desativa) um plano. Requer permissão de administrador.")
    public ResponseEntity<Void> deletarPlano(@PathVariable PlanoTipo tipo) {
        planoService.deletarPlano(tipo);
        return ResponseEntity.noContent().build();
    }
}
