package com.venifretes.security;

import com.venifretes.model.entity.Usuario;
import com.venifretes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .authorities(Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())))
            .accountExpired(false)
            .accountLocked(!usuario.getAtivo())
            .credentialsExpired(false)
            .disabled(!usuario.getAtivo())
            .build();
    }
}
