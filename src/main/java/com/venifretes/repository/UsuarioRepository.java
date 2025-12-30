package com.venifretes.repository;

import com.venifretes.model.entity.Usuario;
import com.venifretes.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    Page<Usuario> findByRole(Role role, Pageable pageable);
}
