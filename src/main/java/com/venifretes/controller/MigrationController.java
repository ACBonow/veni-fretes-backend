package com.venifretes.controller;

import com.venifretes.service.migration.FreteiroMigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/migration")
@RequiredArgsConstructor
@Tag(name = "Migration", description = "Admin endpoints for data migration")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class MigrationController {

    private final FreteiroMigrationService migrationService;

    @PostMapping("/freteiros/test")
    @Operation(summary = "Test migration with sample data")
    public ResponseEntity<?> testMigration() {
        try {
            // Create a test migration
            FreteiroMigrationService.OldFreteiroData testData = new FreteiroMigrationService.OldFreteiroData();
            testData.nome = "Freteiro Teste";
            testData.telefone = "53999999999";
            testData.email = "teste@teste.com";
            testData.veiculo = "Camionete, Caminhão";
            testData.dias = "Segunda a Sexta, Sábado";
            testData.modelo = "Municipal, Intermunicipal";
            testData.mudanca = "Sim";
            testData.estado = "RS";
            testData.cidade = "Pelotas";

            var result = migrationService.migrateFreteiro(testData);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Test migration successful",
                "freteiroId", result.getId(),
                "nome", result.getNome(),
                "slug", result.getSlug(),
                "horariosCount", result.getHorariosAtendimento().size()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Check migration system status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(Map.of(
            "status", "ready",
            "message", "Migration system is ready. Use /freteiros/test to test migration."
        ));
    }

    @PostMapping("/freteiros/batch")
    @Operation(summary = "Migrate multiple freteiros from old database")
    public ResponseEntity<?> migrateBatch(@RequestBody List<FreteiroMigrationService.OldFreteiroData> freteiros) {
        try {
            List<Map<String, Object>> results = new ArrayList<>();
            int success = 0;
            int failed = 0;

            for (FreteiroMigrationService.OldFreteiroData data : freteiros) {
                try {
                    var result = migrationService.migrateFreteiro(data);
                    results.add(Map.of(
                        "nome", result.getNome(),
                        "id", result.getId(),
                        "status", "success"
                    ));
                    success++;
                } catch (Exception e) {
                    results.add(Map.of(
                        "nome", data.nome,
                        "status", "failed",
                        "error", e.getMessage()
                    ));
                    failed++;
                }
            }

            return ResponseEntity.ok(Map.of(
                "total", freteiros.size(),
                "success", success,
                "failed", failed,
                "results", results
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }
}
