package com.venifretes.controller;

import com.venifretes.dto.response.PlanoResponse;
import com.venifretes.model.entity.Plano;
import com.venifretes.model.enums.PlanoTipo;
import com.venifretes.repository.PlanoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planos")
@RequiredArgsConstructor
@Tag(name = "Planos", description = "Planos de assinatura disponíveis")
public class PlanosController {

    private final PlanoRepository planoRepository;

    @GetMapping
    @Operation(summary = "Listar todos os planos disponíveis",
            description = "Retorna todos os planos ativos ordenados por ordem de exibição")
    public ResponseEntity<List<PlanoResponse>> listarPlanos() {
        List<Plano> planos = planoRepository.findAllByAtivoTrueOrderByOrdem();

        List<PlanoResponse> response = planos.stream()
                .map(plano -> PlanoResponse.builder()
                        .id(plano.getId())
                        .nome(plano.getNome())
                        .preco(plano.getPreco())
                        .features(plano.getFeatures())
                        .posicaoRanking(plano.getPosicaoRanking())
                        .limiteFotos(plano.getLimiteFotos())
                        .ordem(plano.getOrdem())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipo}")
    @Operation(summary = "Buscar plano por tipo",
            description = "Retorna os detalhes de um plano específico")
    public ResponseEntity<PlanoResponse> buscarPorTipo(@PathVariable PlanoTipo tipo) {
        Plano plano = planoRepository.findById(tipo)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        PlanoResponse response = PlanoResponse.builder()
                .id(plano.getId())
                .nome(plano.getNome())
                .preco(plano.getPreco())
                .features(plano.getFeatures())
                .posicaoRanking(plano.getPosicaoRanking())
                .limiteFotos(plano.getLimiteFotos())
                .ordem(plano.getOrdem())
                .build();

        return ResponseEntity.ok(response);
    }
}
