package com.venifretes.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException)
            throws IOException, ServletException {

        log.warn("Tentativa de acesso não autorizado: uri={}, ip={}, message={}",
            request.getRequestURI(),
            request.getRemoteAddr(),
            authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
    }
}
