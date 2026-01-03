package com.venifretes.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WhatsAppServiceImpl implements WhatsAppService {

    @Value("${whatsapp.enabled:false}")
    private boolean enabled;

    @Override
    public void enviarNotificacao(String telefone, String mensagem) {
        if (!enabled) {
            log.warn("Tentativa de envio de WhatsApp com serviço desabilitado (fallback ativo): telefone={}, mensagem={}",
                    telefone, mensagem);
            return;
        }

        // TODO: Implementar integração real com API de WhatsApp
        log.info("Enviando WhatsApp para {}: {}", telefone, mensagem);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
