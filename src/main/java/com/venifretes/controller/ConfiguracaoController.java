package com.venifretes.controller;

import com.venifretes.dto.request.ConfiguracaoRequest;
import com.venifretes.dto.response.ConfiguracaoResponse;
import com.venifretes.model.enums.TipoConfiguracao;
import com.venifretes.service.configuracao.ConfiguracaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracoes")
@RequiredArgsConstructor
@Tag(name = "Configurações do Sistema", description = "Gerenciamento de configurações do sistema")
@PreAuthorize("hasRole('ADMIN')")
public class ConfiguracaoController {

    private final ConfiguracaoService configuracaoService;

    @GetMapping
    @Operation(summary = "[ADMIN] Listar todas as configurações",
            description = "Retorna todas as configurações do sistema. Requer permissão de administrador.")
    public ResponseEntity<List<ConfiguracaoResponse>> listarConfiguracoes() {
        List<ConfiguracaoResponse> configuracoes = configuracaoService.listarTodasConfiguracoes();
        return ResponseEntity.ok(configuracoes);
    }

    @GetMapping("/{chave}")
    @Operation(summary = "[ADMIN] Buscar configuração específica",
            description = "Retorna uma configuração específica por sua chave. Requer permissão de administrador.")
    public ResponseEntity<ConfiguracaoResponse> buscarConfiguracao(@PathVariable TipoConfiguracao chave) {
        ConfiguracaoResponse config = configuracaoService.buscarConfiguracao(chave);
        return ResponseEntity.ok(config);
    }

    @PutMapping("/{chave}")
    @Operation(summary = "[ADMIN] Atualizar configuração",
            description = "Atualiza o valor de uma configuração específica. Requer permissão de administrador.")
    public ResponseEntity<ConfiguracaoResponse> atualizarConfiguracao(
            @PathVariable TipoConfiguracao chave,
            @Valid @RequestBody ConfiguracaoRequest request) {
        ConfiguracaoResponse config = configuracaoService.atualizarConfiguracao(chave, request);
        return ResponseEntity.ok(config);
    }

    @PostMapping("/{chave}/restaurar")
    @Operation(summary = "[ADMIN] Restaurar configuração para valor padrão",
            description = "Restaura uma configuração para seu valor padrão. Requer permissão de administrador.")
    public ResponseEntity<ConfiguracaoResponse> restaurarPadrao(@PathVariable TipoConfiguracao chave) {
        ConfiguracaoResponse config = configuracaoService.restaurarPadrao(chave);
        return ResponseEntity.ok(config);
    }

    @PostMapping("/inicializar")
    @Operation(summary = "[ADMIN] Inicializar configurações padrão",
            description = "Inicializa todas as configurações com valores padrão se ainda não existirem. Requer permissão de administrador.")
    public ResponseEntity<Void> inicializarConfiguracoesPadrao() {
        configuracaoService.inicializarConfiguracoesPadrao();
        return ResponseEntity.ok().build();
    }
}
