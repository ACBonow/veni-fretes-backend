package com.venifretes.controller;

import com.venifretes.dto.request.PessoaFilterRequest;
import com.venifretes.dto.request.UpdateUserRoleRequest;
import com.venifretes.dto.response.PessoaListResponse;
import com.venifretes.dto.response.UserListResponse;
import com.venifretes.model.enums.Role;
import com.venifretes.service.user.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints de gerenciamento de usuários (Admin)")
@SecurityRequirement(name = "bearer-jwt")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Lista todos os usuários com paginação")
    public ResponseEntity<Page<UserListResponse>> listAllUsers(
            @Parameter(description = "Número da página (inicia em 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<UserListResponse> users = userManagementService.listAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Listar usuários por role", description = "Lista usuários filtrados por role")
    public ResponseEntity<Page<UserListResponse>> listUsersByRole(
            @PathVariable Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserListResponse> users = userManagementService.listUsersByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UserListResponse> getUserById(@PathVariable Long userId) {
        UserListResponse user = userManagementService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}/role")
    @Operation(summary = "Atualizar role do usuário", description = "Promove ou rebaixa um usuário (ex: transformar em ADMIN)")
    public ResponseEntity<UserListResponse> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {
        UserListResponse updated = userManagementService.updateUserRole(userId, request);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{userId}/toggle-status")
    @Operation(summary = "Ativar/desativar usuário", description = "Alterna o status ativo/inativo do usuário")
    public ResponseEntity<UserListResponse> toggleUserStatus(@PathVariable Long userId) {
        UserListResponse updated = userManagementService.toggleUserStatus(userId);
        return ResponseEntity.ok(updated);
    }

    // ==================== PEOPLE MANAGEMENT - ADVANCED FILTERING ====================

    @GetMapping("/pessoas")
    @Operation(
        summary = "Listar todas as pessoas (UNIFICADO)",
        description = "Lista todos os usuários, freteiros, contratantes e admins em uma única tabela com filtros avançados"
    )
    public ResponseEntity<Page<PessoaListResponse>> listAllPeople(
            @Parameter(description = "Buscar por nome (contém, case-insensitive)") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtrar por email (contém)") @RequestParam(required = false) String email,
            @Parameter(description = "Filtrar por role") @RequestParam(required = false) Role role,
            @Parameter(description = "Filtrar por status ativo/inativo") @RequestParam(required = false) Boolean ativo,
            @Parameter(description = "Filtrar por cidade (apenas freteiros)") @RequestParam(required = false) String cidade,
            @Parameter(description = "Filtrar por verificado (apenas freteiros)") @RequestParam(required = false) Boolean verificado,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenação") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        PessoaFilterRequest filters = PessoaFilterRequest.builder()
                .nome(nome)
                .email(email)
                .role(role)
                .ativo(ativo)
                .cidade(cidade)
                .verificado(verificado)
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PessoaListResponse> pessoas = userManagementService.listAllPeople(filters, pageable);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/freteiros")
    @Operation(
        summary = "Listar freteiros (ROLE-SPECIFIC)",
        description = "Lista apenas freteiros com filtros específicos incluindo cidade e verificação"
    )
    public ResponseEntity<Page<PessoaListResponse>> listFreteiros(
            @Parameter(description = "Buscar por nome") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtrar por cidade") @RequestParam(required = false) String cidade,
            @Parameter(description = "Filtrar por status ativo") @RequestParam(required = false) Boolean ativo,
            @Parameter(description = "Filtrar por verificado") @RequestParam(required = false) Boolean verificado,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenação") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        PessoaFilterRequest filters = PessoaFilterRequest.builder()
                .nome(nome)
                .cidade(cidade)
                .ativo(ativo)
                .verificado(verificado)
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PessoaListResponse> freteiros = userManagementService.listFreteiros(filters, pageable);
        return ResponseEntity.ok(freteiros);
    }

    @GetMapping("/contratantes")
    @Operation(
        summary = "Listar contratantes (ROLE-SPECIFIC)",
        description = "Lista apenas contratantes com filtros (Note: Contratantes não têm campo 'ativo')"
    )
    public ResponseEntity<Page<PessoaListResponse>> listContratantes(
            @Parameter(description = "Buscar por nome") @RequestParam(required = false) String nome,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenação") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        PessoaFilterRequest filters = PessoaFilterRequest.builder()
                .nome(nome)
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PessoaListResponse> contratantes = userManagementService.listContratantes(filters, pageable);
        return ResponseEntity.ok(contratantes);
    }

    @GetMapping("/admins")
    @Operation(
        summary = "Listar administradores (ROLE-SPECIFIC)",
        description = "Lista apenas usuários com role ADMIN"
    )
    public ResponseEntity<Page<PessoaListResponse>> listAdmins(
            @Parameter(description = "Buscar por nome") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtrar por status ativo") @RequestParam(required = false) Boolean ativo,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenação") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        PessoaFilterRequest filters = PessoaFilterRequest.builder()
                .nome(nome)
                .ativo(ativo)
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PessoaListResponse> admins = userManagementService.listAdmins(filters, pageable);
        return ResponseEntity.ok(admins);
    }
}
