package com.venifretes.controller;

import com.venifretes.dto.request.UpdatePerfilRequest;
import com.venifretes.dto.response.DashboardResponse;
import com.venifretes.dto.response.UserResponse;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.Usuario;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.repository.UsuarioRepository;
import com.venifretes.service.dashboard.DashboardService;
import com.venifretes.service.freteiro.CompletudeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freteiro/perfil")
@RequiredArgsConstructor
@Tag(name = "Perfil Freteiro", description = "Gerenciamento do perfil do freteiro autenticado")
@SecurityRequirement(name = "bearer-jwt")
public class FreteiroPerfilController {

    private final UsuarioRepository usuarioRepository;
    private final FreteiroRepository freteiroRepository;
    private final CompletudeService completudeService;

    @GetMapping
    @Operation(summary = "Obter perfil do freteiro autenticado")
    public ResponseEntity<UserResponse> getMeuPerfil() {
        Freteiro freteiro = getAuthenticatedFreteiro();

        UserResponse response = UserResponse.builder()
                .id(freteiro.getId())
                .nome(freteiro.getNome())
                .email(freteiro.getEmail())
                .telefone(freteiro.getTelefone())
                .role(freteiro.getRole())
                .emailVerificado(freteiro.getEmailVerificado())
                .ativo(freteiro.getAtivo())
                .createdAt(freteiro.getCreatedAt())
                .slug(freteiro.getSlug())
                .cidade(freteiro.getCidade())
                .estado(freteiro.getEstado())
                .areasAtendidas(freteiro.getAreasAtendidas())
                .descricao(freteiro.getDescricao())
                .fotoPerfil(freteiro.getFotoPerfil())
                .fotosVeiculo(freteiro.getFotosVeiculo())
                .tiposVeiculo(freteiro.getTiposVeiculo())
                .tiposServico(freteiro.getTiposServico())
                .porcentagemCompletude(freteiro.getPorcentagemCompletude())
                .avaliacaoMedia(freteiro.getAvaliacaoMedia())
                .totalAvaliacoes(freteiro.getTotalAvaliacoes())
                .verificado(freteiro.getVerificado())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Atualizar perfil do freteiro",
            description = "Atualiza informações do perfil do freteiro autenticado")
    public ResponseEntity<UserResponse> updatePerfil(@Valid @RequestBody UpdatePerfilRequest request) {
        Freteiro freteiro = getAuthenticatedFreteiro();

        // Atualizar apenas os campos que foram fornecidos
        if (request.getNome() != null) {
            freteiro.setNome(request.getNome());
        }
        if (request.getTelefone() != null) {
            freteiro.setTelefone(request.getTelefone());
        }
        if (request.getCidade() != null) {
            freteiro.setCidade(request.getCidade());
        }
        if (request.getEstado() != null) {
            freteiro.setEstado(request.getEstado());
        }
        if (request.getAreasAtendidas() != null) {
            freteiro.setAreasAtendidas(request.getAreasAtendidas());
        }
        if (request.getDescricao() != null) {
            freteiro.setDescricao(request.getDescricao());
        }
        if (request.getTiposVeiculo() != null) {
            freteiro.setTiposVeiculo(request.getTiposVeiculo());
        }
        if (request.getTiposServico() != null) {
            freteiro.setTiposServico(request.getTiposServico());
        }

        // Recalcular porcentagem de completude antes de salvar
        completudeService.atualizarCompletude(freteiro);

        Freteiro updated = freteiroRepository.save(freteiro);

        UserResponse response = UserResponse.builder()
                .id(updated.getId())
                .nome(updated.getNome())
                .email(updated.getEmail())
                .telefone(updated.getTelefone())
                .role(updated.getRole())
                .emailVerificado(updated.getEmailVerificado())
                .ativo(updated.getAtivo())
                .createdAt(updated.getCreatedAt())
                .slug(updated.getSlug())
                .cidade(updated.getCidade())
                .estado(updated.getEstado())
                .areasAtendidas(updated.getAreasAtendidas())
                .descricao(updated.getDescricao())
                .fotoPerfil(updated.getFotoPerfil())
                .fotosVeiculo(updated.getFotosVeiculo())
                .tiposVeiculo(updated.getTiposVeiculo())
                .tiposServico(updated.getTiposServico())
                .porcentagemCompletude(updated.getPorcentagemCompletude())
                .avaliacaoMedia(updated.getAvaliacaoMedia())
                .totalAvaliacoes(updated.getTotalAvaliacoes())
                .verificado(updated.getVerificado())
                .build();

        return ResponseEntity.ok(response);
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
