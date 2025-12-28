package com.venifretes.controller;

import com.venifretes.dto.filter.FreteiroFilterDTO;
import com.venifretes.dto.request.AvaliacaoRequest;
import com.venifretes.dto.response.AvaliacaoResponse;
import com.venifretes.dto.response.AvaliacaoStatsResponse;
import com.venifretes.dto.response.FreteiroDetailResponse;
import com.venifretes.dto.response.FreteiroListResponse;
import com.venifretes.service.avaliacao.AvaliacaoService;
import com.venifretes.service.freteiro.FreteiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freteiros")
@RequiredArgsConstructor
@Tag(name = "Freteiros (Público)", description = "Endpoints públicos de freteiros")
public class FreteiroController {

    private final FreteiroService freteiroService;
    private final AvaliacaoService avaliacaoService;

    @GetMapping
    @Operation(summary = "Listar freteiros com filtros e ranking")
    public ResponseEntity<Page<FreteiroListResponse>> listar(
            @ModelAttribute FreteiroFilterDTO filter,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<FreteiroListResponse> freteiros = freteiroService.buscarFreteiros(filter, pageable);
        return ResponseEntity.ok(freteiros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar freteiro por ID")
    public ResponseEntity<FreteiroDetailResponse> buscarPorId(@PathVariable Long id) {
        FreteiroDetailResponse freteiro = freteiroService.buscarPorId(id);
        return ResponseEntity.ok(freteiro);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Buscar freteiro por slug")
    public ResponseEntity<FreteiroDetailResponse> buscarPorSlug(@PathVariable String slug) {
        FreteiroDetailResponse freteiro = freteiroService.buscarPorSlug(slug);
        return ResponseEntity.ok(freteiro);
    }

    @GetMapping("/{id}/avaliacoes")
    @Operation(summary = "Listar avaliações do freteiro",
            description = "Lista todas as avaliações aprovadas de um freteiro")
    public ResponseEntity<Page<AvaliacaoResponse>> listarAvaliacoes(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AvaliacaoResponse> avaliacoes = avaliacaoService.listarAvaliacoes(id, pageable);
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping("/{id}/avaliacoes")
    @Operation(summary = "Criar avaliação",
            description = "Cria uma nova avaliação para o freteiro (pendente de aprovação)")
    public ResponseEntity<AvaliacaoResponse> criarAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequest request) {

        AvaliacaoResponse avaliacao = avaliacaoService.criarAvaliacao(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }

    @GetMapping("/{id}/avaliacoes/stats")
    @Operation(summary = "Estatísticas de avaliações",
            description = "Retorna estatísticas das avaliações do freteiro")
    public ResponseEntity<AvaliacaoStatsResponse> obterEstatisticas(@PathVariable Long id) {
        AvaliacaoStatsResponse stats = avaliacaoService.obterEstatisticas(id);
        return ResponseEntity.ok(stats);
    }
}
