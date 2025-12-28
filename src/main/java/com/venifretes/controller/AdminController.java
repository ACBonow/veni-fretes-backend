package com.venifretes.controller;

import com.venifretes.dto.response.FreteiroDetailResponse;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.service.freteiro.FreteiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Endpoints administrativos")
@SecurityRequirement(name = "bearer-jwt")
public class AdminController {

    private final FreteiroRepository freteiroRepository;
    private final FreteiroService freteiroService;

    @GetMapping("/freteiros")
    @Operation(summary = "Listar todos os freteiros",
            description = "Lista todos os freteiros cadastrados (admin)")
    public ResponseEntity<Page<FreteiroDetailResponse>> listarTodosFreteiros(
            @PageableDefault(size = 20) Pageable pageable) {

        Page<Freteiro> freteiros = freteiroRepository.findAll(pageable);
        Page<FreteiroDetailResponse> response = freteiros.map(freteiroService::toDetailResponse);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/freteiros/{id}/verificar")
    @Operation(summary = "Verificar freteiro",
            description = "Marca um freteiro como verificado")
    public ResponseEntity<Map<String, Object>> verificarFreteiro(@PathVariable Long id) {
        Freteiro freteiro = freteiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        freteiro.setVerificado(true);
        freteiro.setDataVerificacao(LocalDateTime.now());
        freteiroRepository.save(freteiro);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freteiro verificado com sucesso");
        response.put("freteiroId", id);
        response.put("verificado", true);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/freteiros/{id}/desverificar")
    @Operation(summary = "Remover verificação do freteiro",
            description = "Remove o status de verificado de um freteiro")
    public ResponseEntity<Map<String, Object>> desverificarFreteiro(@PathVariable Long id) {
        Freteiro freteiro = freteiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        freteiro.setVerificado(false);
        freteiro.setDataVerificacao(null);
        freteiroRepository.save(freteiro);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Verificação removida com sucesso");
        response.put("freteiroId", id);
        response.put("verificado", false);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/freteiros/{id}/ativar")
    @Operation(summary = "Ativar freteiro",
            description = "Ativa um freteiro desativado")
    public ResponseEntity<Map<String, Object>> ativarFreteiro(@PathVariable Long id) {
        Freteiro freteiro = freteiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        freteiro.setAtivo(true);
        freteiroRepository.save(freteiro);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freteiro ativado com sucesso");
        response.put("freteiroId", id);
        response.put("ativo", true);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/freteiros/{id}/desativar")
    @Operation(summary = "Desativar freteiro",
            description = "Desativa um freteiro")
    public ResponseEntity<Map<String, Object>> desativarFreteiro(@PathVariable Long id) {
        Freteiro freteiro = freteiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freteiro não encontrado"));

        freteiro.setAtivo(false);
        freteiroRepository.save(freteiro);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Freteiro desativado com sucesso");
        response.put("freteiroId", id);
        response.put("ativo", false);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Estatísticas gerais",
            description = "Retorna estatísticas gerais do sistema")
    public ResponseEntity<Map<String, Object>> getEstatisticas() {
        long totalFreteiros = freteiroRepository.count();
        long freteirosVerificados = freteiroRepository.countByVerificadoTrue();
        long freteirosAtivos = freteiroRepository.countByAtivoTrue();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFreteiros", totalFreteiros);
        stats.put("freteirosVerificados", freteirosVerificados);
        stats.put("freteirosAtivos", freteirosAtivos);
        stats.put("freteirosInativos", totalFreteiros - freteirosAtivos);

        return ResponseEntity.ok(stats);
    }
}
