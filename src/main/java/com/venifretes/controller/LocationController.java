package com.venifretes.controller;

import com.venifretes.dto.response.CidadeDTO;
import com.venifretes.model.entity.Cidade;
import com.venifretes.repository.CidadeRepository;
import com.venifretes.service.location.IBGEService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Tag(name = "Localização", description = "Endpoints para estados e cidades")
public class LocationController {

    private final CidadeRepository cidadeRepository;
    private final IBGEService ibgeService;

    @GetMapping("/cidades/{estadoSigla}")
    @Operation(summary = "Listar cidades por estado")
    public ResponseEntity<List<CidadeDTO>> getCidadesByEstado(
        @PathVariable String estadoSigla
    ) {
        List<Cidade> cidades = cidadeRepository.findByEstadoSiglaOrderByNome(estadoSigla);

        List<CidadeDTO> response = cidades.stream()
            .map(this::toCidadeDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cidades/search")
    @Operation(summary = "Buscar cidade por nome")
    public ResponseEntity<List<CidadeDTO>> searchCidades(
        @RequestParam String nome,
        @RequestParam(required = false, defaultValue = "RS") String estado
    ) {
        List<Cidade> cidades = cidadeRepository.findByEstadoSiglaOrderByNome(estado).stream()
            .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
            .limit(10)
            .collect(Collectors.toList());

        List<CidadeDTO> response = cidades.stream()
            .map(this::toCidadeDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/import-rs")
    @Operation(summary = "Importar cidades do RS (admin only)")
    @SecurityRequirement(name = "bearer-jwt")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importRioGrandeDoSul() {
        try {
            ibgeService.importRioGrandeDoSul();
            long count = cidadeRepository.countByEstado(
                cidadeRepository.findById(1).get().getEstado()
            );
            return ResponseEntity.ok("Successfully imported " + count + " cities from Rio Grande do Sul");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import failed: " + e.getMessage());
        }
    }

    private CidadeDTO toCidadeDTO(Cidade cidade) {
        return CidadeDTO.builder()
            .id(cidade.getId())
            .nome(cidade.getNome())
            .estadoSigla(cidade.getEstado().getSigla())
            .estadoNome(cidade.getEstado().getNome())
            .codigoIbge(cidade.getCodigoIbge())
            .build();
    }
}
