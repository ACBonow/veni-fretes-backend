package com.venifretes.service.auth;

import com.venifretes.dto.request.LoginRequest;
import com.venifretes.dto.request.RegisterRequest;
import com.venifretes.dto.response.AuthResponse;
import com.venifretes.dto.response.UserResponse;
import com.venifretes.exception.BusinessException;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.Usuario;
import com.venifretes.model.enums.Role;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.repository.UsuarioRepository;
import com.venifretes.security.JwtTokenProvider;
import com.venifretes.service.freteiro.FreteiroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final FreteiroRepository freteiroRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final FreteiroService freteiroService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validações
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.warn("Tentativa de registro com email duplicado: email={}", request.getEmail());
            throw new BusinessException("Email já cadastrado");
        }
        if (usuarioRepository.existsByTelefone(request.getTelefone())) {
            log.warn("Tentativa de registro com telefone duplicado: telefone={}", request.getTelefone());
            throw new BusinessException("Telefone já cadastrado");
        }

        // Criar freteiro
        String slug = freteiroService.gerarSlugUnico(request.getNome());

        Freteiro freteiro = Freteiro.builder()
            .nome(request.getNome())
            .email(request.getEmail())
            .telefone(request.getTelefone())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.FRETEIRO)
            .slug(slug)
            .cidade(request.getCidade())
            .estado(request.getEstado())
            .emailVerificado(false)
            .ativo(true)
            .build();

        Freteiro saved = freteiroRepository.save(freteiro);

        // Gerar tokens
        String accessToken = tokenProvider.generateAccessToken(saved.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(saved.getEmail());

        log.info("Novo freteiro registrado: {}", saved.getEmail());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(tokenProvider.getJwtExpiration())
            .userId(saved.getId())
            .email(saved.getEmail())
            .nome(saved.getNome())
            .role(saved.getRole())
            .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Buscar usuário e atualizar último login
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        usuario.setLastLoginAt(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String accessToken = tokenProvider.generateAccessToken(request.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(request.getEmail());

        log.info("Login realizado: {}", request.getEmail());

        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(tokenProvider.getJwtExpiration())
            .userId(usuario.getId())
            .email(usuario.getEmail())
            .nome(usuario.getNome())
            .role(usuario.getRole())
            .build();
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Tentativa de acesso com autenticação inválida: authenticated={}",
                authentication != null ? authentication.isAuthenticated() : "null");
            throw new BusinessException("Usuário não autenticado");
        }

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        UserResponse.UserResponseBuilder builder = UserResponse.builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .telefone(usuario.getTelefone())
            .role(usuario.getRole())
            .emailVerificado(usuario.getEmailVerificado())
            .ativo(usuario.getAtivo())
            .createdAt(usuario.getCreatedAt());

        // Se for freteiro, adicionar campos específicos
        if (usuario instanceof Freteiro freteiro) {
            builder
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
                .verificado(freteiro.getVerificado());
        }

        return builder.build();
    }
}
