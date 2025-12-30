package com.venifretes.service.user;

import com.venifretes.dto.request.UpdateUserRoleRequest;
import com.venifretes.dto.response.UserListResponse;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Usuario;
import com.venifretes.model.enums.Role;
import com.venifretes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagementService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todos os usuários com paginação
     */
    @Transactional(readOnly = true)
    public Page<UserListResponse> listAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
            .map(this::toUserListResponse);
    }

    /**
     * Lista usuários por role
     */
    @Transactional(readOnly = true)
    public Page<UserListResponse> listUsersByRole(Role role, Pageable pageable) {
        return usuarioRepository.findByRole(role, pageable)
            .map(this::toUserListResponse);
    }

    /**
     * Atualiza a role de um usuário
     */
    @Transactional
    public UserListResponse updateUserRole(Long userId, UpdateUserRoleRequest request) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Role oldRole = usuario.getRole();
        usuario.setRole(request.getRole());
        Usuario saved = usuarioRepository.save(usuario);

        log.info("User role updated: userId={}, oldRole={}, newRole={}",
                 userId, oldRole, request.getRole());

        return toUserListResponse(saved);
    }

    /**
     * Ativa ou desativa um usuário
     */
    @Transactional
    public UserListResponse toggleUserStatus(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean newStatus = !usuario.getAtivo();
        usuario.setAtivo(newStatus);
        Usuario saved = usuarioRepository.save(usuario);

        log.info("User status toggled: userId={}, newStatus={}", userId, newStatus);

        return toUserListResponse(saved);
    }

    /**
     * Busca usuário por ID
     */
    @Transactional(readOnly = true)
    public UserListResponse getUserById(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return toUserListResponse(usuario);
    }

    /**
     * Converte Usuario para UserListResponse
     */
    private UserListResponse toUserListResponse(Usuario usuario) {
        return UserListResponse.builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .telefone(usuario.getTelefone())
            .role(usuario.getRole())
            .emailVerificado(usuario.getEmailVerificado())
            .ativo(usuario.getAtivo())
            .createdAt(usuario.getCreatedAt())
            .lastLoginAt(usuario.getLastLoginAt())
            .build();
    }
}
