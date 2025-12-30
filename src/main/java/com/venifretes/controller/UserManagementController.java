package com.venifretes.controller;

import com.venifretes.dto.request.UpdateUserRoleRequest;
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
}
