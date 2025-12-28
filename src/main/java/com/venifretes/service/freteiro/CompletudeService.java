package com.venifretes.service.freteiro;

import com.venifretes.model.entity.Freteiro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompletudeService {

    private static final int PESO_FOTO_PERFIL = 20;
    private static final int PESO_FOTOS_VEICULO = 25;
    private static final int PESO_DESCRICAO = 20;
    private static final int PESO_AREAS_ATENDIDAS = 15;
    private static final int PESO_TIPOS_SERVICO = 10;
    private static final int PESO_TIPOS_VEICULO = 10;

    public int calcularCompletude(Freteiro freteiro) {
        int pontuacao = 0;

        // Foto de perfil
        if (freteiro.getFotoPerfil() != null && !freteiro.getFotoPerfil().isBlank()) {
            pontuacao += PESO_FOTO_PERFIL;
        }

        // Fotos do veículo (mínimo 3)
        if (freteiro.getFotosVeiculo() != null && freteiro.getFotosVeiculo().size() >= 3) {
            pontuacao += PESO_FOTOS_VEICULO;
        }

        // Descrição
        if (freteiro.getDescricao() != null && freteiro.getDescricao().length() >= 50) {
            pontuacao += PESO_DESCRICAO;
        }

        // Áreas atendidas
        if (freteiro.getAreasAtendidas() != null && !freteiro.getAreasAtendidas().isEmpty()) {
            pontuacao += PESO_AREAS_ATENDIDAS;
        }

        // Tipos de serviço
        if (freteiro.getTiposServico() != null && !freteiro.getTiposServico().isEmpty()) {
            pontuacao += PESO_TIPOS_SERVICO;
        }

        // Tipos de veículo
        if (freteiro.getTiposVeiculo() != null && !freteiro.getTiposVeiculo().isEmpty()) {
            pontuacao += PESO_TIPOS_VEICULO;
        }

        log.debug("Completude calculada para freteiro {}: {}%", freteiro.getId(), pontuacao);
        return pontuacao;
    }

    public void atualizarCompletude(Freteiro freteiro) {
        int completude = calcularCompletude(freteiro);
        freteiro.setPorcentagemCompletude(completude);
    }
}
