package com.venifretes.service.user;

import com.venifretes.dto.request.PessoaFilterRequest;
import com.venifretes.dto.request.UpdateUserRoleRequest;
import com.venifretes.dto.response.PessoaListResponse;
import com.venifretes.dto.response.UserListResponse;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Admin;
import com.venifretes.model.entity.Contratante;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.Usuario;
import com.venifretes.model.enums.Role;
import com.venifretes.repository.ContratanteRepository;
import com.venifretes.repository.FreteiroRepository;
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
    private final FreteiroRepository freteiroRepository;
    private final ContratanteRepository contratanteRepository;

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

    // ==================== PEOPLE MANAGEMENT WITH ADVANCED FILTERS ====================

    /**
     * Lista todos os usuários (todas as roles) com filtros avançados
     */
    @Transactional(readOnly = true)
    public Page<PessoaListResponse> listAllPeople(PessoaFilterRequest filters, Pageable pageable) {
        Page<Usuario> usuarios;

        if (filters.getNome() != null && filters.getRole() != null && filters.getAtivo() != null) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCaseAndRoleAndAtivo(
                filters.getNome(), filters.getRole(), filters.getAtivo(), pageable);
        } else if (filters.getNome() != null && filters.getRole() != null) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCaseAndRole(
                filters.getNome(), filters.getRole(), pageable);
        } else if (filters.getNome() != null && filters.getAtivo() != null) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCaseAndAtivo(
                filters.getNome(), filters.getAtivo(), pageable);
        } else if (filters.getRole() != null && filters.getAtivo() != null) {
            usuarios = usuarioRepository.findByRoleAndAtivo(filters.getRole(), filters.getAtivo(), pageable);
        } else if (filters.getNome() != null) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCase(filters.getNome(), pageable);
        } else if (filters.getRole() != null) {
            usuarios = usuarioRepository.findByRole(filters.getRole(), pageable);
        } else if (filters.getAtivo() != null) {
            usuarios = usuarioRepository.findByAtivo(filters.getAtivo(), pageable);
        } else {
            usuarios = usuarioRepository.findAll(pageable);
        }

        return usuarios.map(this::toPessoaListResponse);
    }

    /**
     * Lista freteiros com filtros avançados (incluindo cidade)
     */
    @Transactional(readOnly = true)
    public Page<PessoaListResponse> listFreteiros(PessoaFilterRequest filters, Pageable pageable) {
        Page<Freteiro> freteiros = freteiroRepository.findByFilters(
            filters.getNome(),
            filters.getCidade(),
            filters.getAtivo(),
            filters.getVerificado(),
            pageable
        );

        return freteiros.map(this::toPessoaListResponse);
    }

    /**
     * Lista contratantes com filtros
     */
    @Transactional(readOnly = true)
    public Page<PessoaListResponse> listContratantes(PessoaFilterRequest filters, Pageable pageable) {
        Page<Contratante> contratantes;

        // Contratante não tem campo 'ativo', apenas nome
        if (filters.getNome() != null) {
            contratantes = contratanteRepository.findByNomeContainingIgnoreCase(filters.getNome(), pageable);
        } else {
            contratantes = contratanteRepository.findAll(pageable);
        }

        return contratantes.map(this::toPessoaListResponse);
    }

    /**
     * Lista administradores com filtros
     */
    @Transactional(readOnly = true)
    public Page<PessoaListResponse> listAdmins(PessoaFilterRequest filters, Pageable pageable) {
        // Admins são usuários com role ADMIN
        Page<Usuario> admins;

        if (filters.getNome() != null && filters.getAtivo() != null) {
            admins = usuarioRepository.findByNomeContainingIgnoreCaseAndRoleAndAtivo(
                filters.getNome(), Role.ADMIN, filters.getAtivo(), pageable);
        } else if (filters.getNome() != null) {
            admins = usuarioRepository.findByNomeContainingIgnoreCaseAndRole(
                filters.getNome(), Role.ADMIN, pageable);
        } else if (filters.getAtivo() != null) {
            admins = usuarioRepository.findByRoleAndAtivo(Role.ADMIN, filters.getAtivo(), pageable);
        } else {
            admins = usuarioRepository.findByRole(Role.ADMIN, pageable);
        }

        return admins.map(this::toPessoaListResponse);
    }

    /**
     * Converte qualquer entidade (Usuario, Freteiro, Contratante, Admin) para PessoaListResponse
     * com campos específicos de cada role
     */
    private PessoaListResponse toPessoaListResponse(Object entity) {
        PessoaListResponse.PessoaListResponseBuilder builder = PessoaListResponse.builder();

        // Campos comuns de Usuario
        if (entity instanceof Usuario) {
            Usuario usuario = (Usuario) entity;
            builder
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .role(usuario.getRole())
                .ativo(usuario.getAtivo())
                .createdAt(usuario.getCreatedAt())
                .lastLoginAt(usuario.getLastLoginAt())
                .emailVerificado(usuario.getEmailVerificado());

            // Campos específicos de Freteiro
            if (entity instanceof Freteiro) {
                Freteiro freteiro = (Freteiro) entity;
                builder
                    .slug(freteiro.getSlug())
                    .cidade(freteiro.getCidade())
                    .estado(freteiro.getEstado())
                    .verificado(freteiro.getVerificado())
                    .avaliacaoMedia(freteiro.getAvaliacaoMedia());
            }
            // Campos específicos de Admin
            else if (entity instanceof Admin) {
                Admin admin = (Admin) entity;
                builder.superAdmin(admin.getSuperAdmin());
            }
        }
        // Campos específicos de Contratante (não herda de Usuario)
        else if (entity instanceof Contratante) {
            Contratante contratante = (Contratante) entity;
            builder
                .id(contratante.getId())
                .nome(contratante.getNome())
                .email(contratante.getEmail())
                .telefone(contratante.getTelefone())
                .role(Role.CONTRATANTE) // Contratantes não herdam de Usuario
                .ativo(true) // Contratante não tem campo 'ativo', assume-se sempre ativo
                .createdAt(contratante.getCreatedAt())
                .emailVerificado(null) // Contratantes não têm email verificado
                .cpfCnpj(contratante.getCpfCnpj());
        }

        return builder.build();
    }
}
