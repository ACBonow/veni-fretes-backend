package com.venifretes.controller;

import com.venifretes.dto.request.ClickTrackingRequest;
import com.venifretes.dto.request.SimpleTrackingRequest;
import com.venifretes.dto.request.TrackingRequest;
import com.venifretes.service.tracking.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
@Tag(name = "Tracking", description = "Rastreamento de eventos")
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping
    @Operation(summary = "Registrar evento de tracking")
    public ResponseEntity<Void> registrar(
            @Valid @RequestBody TrackingRequest request,
            HttpServletRequest httpRequest) {

        // Adicionar IP e User-Agent automaticamente
        String ip = getClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        trackingService.registrarEvento(
            request.getFreteiroId(),
            request.getTipo(),
            ip,
            userAgent,
            request.getOrigem(),
            request.getReferer()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/view")
    @Operation(summary = "Registrar visualização de perfil")
    public ResponseEntity<Void> registrarVisualizacao(
            @Valid @RequestBody SimpleTrackingRequest request,
            HttpServletRequest httpRequest) {

        String ip = getClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        trackingService.registrarEvento(
            request.getFreteiroId(),
            com.venifretes.model.enums.TipoEvento.VISUALIZACAO_PERFIL,
            ip,
            userAgent,
            request.getOrigem(),
            request.getReferer()
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/click")
    @Operation(summary = "Registrar clique em WhatsApp/telefone")
    public ResponseEntity<Void> registrarClique(
            @Valid @RequestBody ClickTrackingRequest request,
            HttpServletRequest httpRequest) {

        String ip = getClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        trackingService.registrarEvento(
            request.getFreteiroId(),
            request.getTipo() != null ? request.getTipo() : com.venifretes.model.enums.TipoEvento.CLIQUE_WHATSAPP,
            ip,
            userAgent,
            request.getOrigem(),
            request.getReferer()
        );

        return ResponseEntity.ok().build();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
