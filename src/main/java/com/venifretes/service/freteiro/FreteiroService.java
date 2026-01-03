package com.venifretes.service.freteiro;

import com.venifretes.dto.filter.FreteiroFilterDTO;
import com.venifretes.dto.response.FreteiroDetailResponse;
import com.venifretes.dto.response.FreteiroListResponse;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.util.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreteiroService {

    private final FreteiroRepository freteiroRepository;
    private final CompletudeService completudeService;

    @Transactional(readOnly = true)
    public Page<FreteiroListResponse> buscarFreteiros(FreteiroFilterDTO filter, Pageable pageable) {
        Page<Freteiro> freteiros = freteiroRepository.findFreteirosRanqueadosCustom(
            filter.getCidade(),
            filter.getEstado(),
            filter.getAvaliacaoMinima(),
            pageable
        );

        return freteiros.map(this::toListResponse);
    }

    @Transactional(readOnly = true)
    public FreteiroDetailResponse buscarPorSlug(String slug) {
        Freteiro freteiro = freteiroRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));

        return toDetailResponse(freteiro);
    }

    @Transactional(readOnly = true)
    public FreteiroDetailResponse buscarPorId(Long id) {
        Freteiro freteiro = freteiroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));

        return toDetailResponse(freteiro);
    }

    public String gerarSlugUnico(String nome) {
        String slug = SlugGenerator.generate(nome);
        String originalSlug = slug;
        int counter = 1;

        while (freteiroRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + counter++;
            if (counter > 100) {
                log.warn("Geração de slug excessiva: nome={}, counter={}", nome, counter);
                break;
            }
        }

        return slug;
    }

    private FreteiroListResponse toListResponse(Freteiro freteiro) {
        return FreteiroListResponse.builder()
            .id(freteiro.getId())
            .nome(freteiro.getNome())
            .slug(freteiro.getSlug())
            .telefone(freteiro.getTelefone())
            .cidade(freteiro.getCidade())
            .estado(freteiro.getEstado())
            .fotoPerfil(freteiro.getFotoPerfil())
            .avaliacaoMedia(freteiro.getAvaliacaoMedia())
            .totalAvaliacoes(freteiro.getTotalAvaliacoes())
            .tiposVeiculo(freteiro.getTiposVeiculo())
            .verificado(freteiro.getVerificado())
            .build();
    }

    public FreteiroDetailResponse toDetailResponse(Freteiro freteiro) {
        return FreteiroDetailResponse.builder()
            .id(freteiro.getId())
            .nome(freteiro.getNome())
            .slug(freteiro.getSlug())
            .telefone(freteiro.getTelefone())
            .email(freteiro.getEmail())
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
            .verificado(freteiro.getVerificado())
            .build();
    }
}
