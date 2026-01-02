package com.venifretes.repository;

import com.venifretes.model.entity.Usuario;
import com.venifretes.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Page<Usuario> findByRole(Role role, Pageable pageable);

    // Admin statistics queries
    long countByAtivoTrue();
    long countByAtivoFalse();
    long countByRole(Role role);
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    long countByCreatedAtAfter(LocalDateTime start);

    // People management - advanced search queries
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Usuario> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Usuario> findByNomeContainingIgnoreCaseAndRole(String nome, Role role, Pageable pageable);
    Page<Usuario> findByNomeContainingIgnoreCaseAndAtivo(String nome, Boolean ativo, Pageable pageable);
    Page<Usuario> findByRoleAndAtivo(Role role, Boolean ativo, Pageable pageable);
    Page<Usuario> findByNomeContainingIgnoreCaseAndRoleAndAtivo(String nome, Role role, Boolean ativo, Pageable pageable);
    Page<Usuario> findByAtivo(Boolean ativo, Pageable pageable);
}
