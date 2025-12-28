package com.venifretes.service.notification;

public interface WhatsAppService {
    void enviarNotificacao(String telefone, String mensagem);
    boolean isEnabled();
}
