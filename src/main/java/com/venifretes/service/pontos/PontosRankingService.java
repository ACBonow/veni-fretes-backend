package com.venifretes.service.pontos;

import com.venifretes.dto.pontos.*;
import com.venifretes.exception.BusinessException;
import com.venifretes.exception.ResourceNotFoundException;
import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.HistoricoCompraPontos;
import com.venifretes.model.entity.PontosRanking;
import com.venifretes.model.enums.PlanoTipo;
import com.venifretes.model.enums.StatusPagamento;
import com.venifretes.repository.FreteiroRepository;
import com.venifretes.repository.HistoricoCompraPontosRepository;
import com.venifretes.repository.PontosRankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PontosRankingService {

    private final FreteiroRepository freteiroRepository;
    private final PontosRankingRepository pontosRankingRepository;
    private final HistoricoCompraPontosRepository historicoRepository;

    // Constantes de negócio
    private static final BigDecimal VALOR_BASE_POR_PONTO = new BigDecimal("0.50");
    private static final int MINIMO_PONTOS_COMPRA = 10;
    private static final int MAXIMO_PONTOS_COMPRA = 500;
    private static final int DIAS_VALIDADE_PONTOS = 30;

    /**
     * Simula a compra de pontos e retorna a posição estimada
     */
    @Transactional(readOnly = true)
    public SimulacaoPontosResponse simularCompraPontos(Long freteiroId, String cidade, Integer pontosAdicionais) {
        log.info("Simulando compra de {} pontos para freteiro {} na cidade {}", pontosAdicionais, freteiroId, cidade);

        Freteiro freteiro = buscarFreteiro(freteiroId);
        validarPlanoMaster(freteiro);
        validarQuantidadePontos(pontosAdicionais);

        // Buscar posição atual
        Integer posicaoAtual = calcularPosicaoNoRanking(freteiro, cidade);

        // Buscar pontos atuais
        Integer pontosAtuais = pontosRankingRepository.findByFreteiroIdAndCidade(freteiroId, cidade)
                .map(PontosRanking::getPontosAtivos)
                .orElse(0);

        // Simular nova posição
        Integer novosPontos = pontosAtuais + pontosAdicionais;
        Integer posicaoComPontos = simularPosicaoComPontos(freteiro, cidade, novosPontos);

        // Calcular concorrentes na frente
        List<SimulacaoPontosResponse.ConcorrenteInfo> concorrentes = buscarConcorrentesNaFrente(
                freteiro, cidade, posicaoComPontos
        );

        // Calcular valores
        BigDecimal valorPorPonto = calcularValorPorPonto(pontosAdicionais);
        BigDecimal desconto = calcularDescontoPercentual(pontosAdicionais);
        BigDecimal valorTotal = valorPorPonto.multiply(new BigDecimal(pontosAdicionais))
                .setScale(2, RoundingMode.HALF_UP);

        String mensagem = gerarMensagemSimulacao(posicaoAtual, posicaoComPontos, pontosAdicionais);

        return SimulacaoPontosResponse.builder()
                .posicaoAtual(posicaoAtual)
                .posicaoComPontos(posicaoComPontos)
                .pontosAdicionais(pontosAdicionais)
                .valorPorPonto(valorPorPonto)
                .valorTotal(valorTotal)
                .descontoPercentual(desconto)
                .diasValidade(DIAS_VALIDADE_PONTOS)
                .mensagem(mensagem)
                .concorrentesNaFrente(concorrentes)
                .build();
    }

    /**
     * Realiza a compra de pontos
     */
    @Transactional
    public CompraPontosResponse comprarPontos(Long freteiroId, CompraPontosRequest request) {
        log.info("Iniciando compra de {} pontos para freteiro {} na cidade {}",
                request.getQuantidadePontos(), freteiroId, request.getCidade());

        Freteiro freteiro = buscarFreteiro(freteiroId);
        validarPlanoMaster(freteiro);
        validarQuantidadePontos(request.getQuantidadePontos());

        // Calcular posições
        Integer posicaoAntes = calcularPosicaoNoRanking(freteiro, request.getCidade());
        Integer pontosAtuais = pontosRankingRepository.findByFreteiroIdAndCidade(freteiroId, request.getCidade())
                .map(PontosRanking::getPontosAtivos)
                .orElse(0);
        Integer novosPontos = pontosAtuais + request.getQuantidadePontos();
        Integer posicaoDepois = simularPosicaoComPontos(freteiro, request.getCidade(), novosPontos);

        // Calcular valores
        BigDecimal valorPorPonto = calcularValorPorPonto(request.getQuantidadePontos());
        BigDecimal valorTotal = valorPorPonto.multiply(new BigDecimal(request.getQuantidadePontos()))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal desconto = calcularDescontoPercentual(request.getQuantidadePontos());

        // Criar histórico de compra
        String transacaoId = gerarTransacaoId();

        HistoricoCompraPontos historico = HistoricoCompraPontos.builder()
                .freteiro(freteiro)
                .cidade(request.getCidade())
                .quantidadePontos(request.getQuantidadePontos())
                .valorUnitario(valorPorPonto)
                .valorTotal(valorTotal)
                .descontoPercentual(desconto)
                .metodoPagamento(request.getMetodoPagamento())
                .status(StatusPagamento.PENDENTE)
                .transacaoId(transacaoId)
                .posicaoEstimadaAntes(posicaoAntes)
                .posicaoEstimadaDepois(posicaoDepois)
                .diasValidade(DIAS_VALIDADE_PONTOS)
                .build();

        historico = historicoRepository.save(historico);

        // TODO: Integrar com PagBank para gerar QR Code / Link de pagamento
        // Por enquanto, retornamos dados simulados
        String qrCodePix = switch (request.getMetodoPagamento()) {
            case PIX -> "00020126580014BR.GOV.BCB.PIX..." + transacaoId;
            default -> null;
        };

        String linkPagamento = "https://sandbox.api.pagseguro.com/charges/" + transacaoId;

        return CompraPontosResponse.builder()
                .id(historico.getId())
                .transacaoId(transacaoId)
                .quantidadePontos(request.getQuantidadePontos())
                .valorTotal(valorTotal)
                .descontoPercentual(desconto)
                .metodoPagamento(request.getMetodoPagamento())
                .status(StatusPagamento.PENDENTE)
                .qrCodePix(qrCodePix)
                .linkPagamento(linkPagamento)
                .posicaoEstimada(posicaoDepois)
                .diasValidade(DIAS_VALIDADE_PONTOS)
                .expiraEm(LocalDateTime.now().plusDays(DIAS_VALIDADE_PONTOS))
                .createdAt(historico.getCreatedAt())
                .mensagem(String.format(
                        "Após a aprovação do pagamento, você ficará na posição %d do ranking em %s",
                        posicaoDepois, request.getCidade()
                ))
                .build();
    }

    /**
     * Aprova uma compra de pontos e adiciona os pontos ao freteiro
     */
    @Transactional
    public void aprovarCompra(String transacaoId) {
        HistoricoCompraPontos historico = historicoRepository.findByTransacaoId(transacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));

        if (historico.getStatus() != StatusPagamento.PENDENTE &&
            historico.getStatus() != StatusPagamento.PROCESSANDO) {
            throw new BusinessException("Transação já foi processada");
        }

        historico.aprovarPagamento();
        historicoRepository.save(historico);

        // Adicionar pontos
        adicionarPontosAoFreteiro(historico);

        log.info("Compra de pontos aprovada: {} - {} pontos para freteiro {}",
                transacaoId, historico.getQuantidadePontos(), historico.getFreteiro().getId());
    }

    /**
     * Consulta status dos pontos do freteiro em uma cidade
     */
    @Transactional(readOnly = true)
    public PontosStatusResponse consultarStatus(Long freteiroId, String cidade) {
        PontosRanking pontos = pontosRankingRepository.findByFreteiroIdAndCidade(freteiroId, cidade)
                .orElse(null);

        List<HistoricoCompraResponse> ultimasCompras = buscarUltimasCompras(freteiroId, cidade, 5);

        if (pontos == null) {
            return PontosStatusResponse.builder()
                    .cidade(cidade)
                    .pontosAtivos(0)
                    .pontosGastos(0)
                    .totalPontosComprados(0)
                    .ultimasCompras(ultimasCompras)
                    .build();
        }

        Integer diasRestantes = pontos.getExpiraEm() != null
                ? (int) ChronoUnit.DAYS.between(LocalDateTime.now(), pontos.getExpiraEm())
                : null;

        return PontosStatusResponse.builder()
                .cidade(cidade)
                .pontosAtivos(pontos.getPontosAtivos())
                .pontosGastos(pontos.getPontosGastos())
                .totalPontosComprados(pontos.getTotalPontosComprados())
                .expiraEm(pontos.getExpiraEm())
                .ultimaCompra(pontos.getUltimaCompra())
                .diasRestantes(diasRestantes)
                .ultimasCompras(ultimasCompras)
                .build();
    }

    /**
     * Busca histórico de compras do freteiro
     */
    @Transactional(readOnly = true)
    public Page<HistoricoCompraResponse> buscarHistorico(Long freteiroId, String cidade, Pageable pageable) {
        Page<HistoricoCompraPontos> historico = cidade != null
                ? historicoRepository.findByFreteiroIdAndCidade(freteiroId, cidade, pageable)
                : historicoRepository.findByFreteiroIdOrderByCreatedAtDesc(freteiroId, pageable);

        return historico.map(this::toHistoricoResponse);
    }

    // ========== MÉTODOS AUXILIARES ==========

    private Freteiro buscarFreteiro(Long id) {
        return freteiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freteiro não encontrado"));
    }

    private void validarPlanoMaster(Freteiro freteiro) {
        boolean isMaster = freteiro.getAssinaturaAtiva() != null &&
                freteiro.getAssinaturaAtiva().getPlano().getId() == PlanoTipo.MASTER;

        if (!isMaster) {
            throw new BusinessException("Apenas freteiros com plano MASTER podem comprar pontos extras");
        }
    }

    private void validarQuantidadePontos(Integer quantidade) {
        if (quantidade < MINIMO_PONTOS_COMPRA) {
            throw new BusinessException("Quantidade mínima de pontos é " + MINIMO_PONTOS_COMPRA);
        }
        if (quantidade > MAXIMO_PONTOS_COMPRA) {
            throw new BusinessException("Quantidade máxima de pontos por transação é " + MAXIMO_PONTOS_COMPRA);
        }
    }

    private Integer calcularPosicaoNoRanking(Freteiro freteiro, String cidade) {
        Pageable pageable = PageRequest.of(0, 1000);
        Page<Freteiro> ranking = freteiroRepository.findFreteirosRanqueados(
                cidade, freteiro.getEstado(), null, pageable
        );

        List<Freteiro> lista = ranking.getContent();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(freteiro.getId())) {
                return i + 1;
            }
        }
        return lista.size() + 1;
    }

    private Integer simularPosicaoComPontos(Freteiro freteiro, String cidade, Integer novosPontos) {
        // Conta quantos freteiros na cidade teriam mais pontos
        Long freteirosComMaisPontos = pontosRankingRepository.countFreteiroComMaisPontos(cidade, novosPontos);
        return freteirosComMaisPontos.intValue() + 1;
    }

    private List<SimulacaoPontosResponse.ConcorrenteInfo> buscarConcorrentesNaFrente(
            Freteiro freteiro, String cidade, Integer posicaoAlvo) {

        Pageable pageable = PageRequest.of(0, posicaoAlvo);
        Page<Freteiro> ranking = freteiroRepository.findFreteirosRanqueados(
                cidade, freteiro.getEstado(), null, pageable
        );

        return ranking.getContent().stream()
                .filter(f -> !f.getId().equals(freteiro.getId()))
                .limit(Math.min(3, posicaoAlvo - 1))
                .map(f -> {
                    Integer pontos = pontosRankingRepository.findByFreteiroIdAndCidade(f.getId(), cidade)
                            .map(PontosRanking::getPontosAtivos)
                            .orElse(0);

                    String plano = f.getAssinaturaAtiva() != null
                            ? f.getAssinaturaAtiva().getPlano().getId().name()
                            : "BASICO";

                    return SimulacaoPontosResponse.ConcorrenteInfo.builder()
                            .nome(f.getNome())
                            .plano(plano)
                            .pontos(pontos)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calcularValorPorPonto(Integer quantidade) {
        BigDecimal desconto = calcularDescontoPercentual(quantidade);
        return VALOR_BASE_POR_PONTO.multiply(BigDecimal.ONE.subtract(desconto.divide(new BigDecimal("100"))))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularDescontoPercentual(Integer quantidade) {
        if (quantidade >= 500) return new BigDecimal("40");
        if (quantidade >= 200) return new BigDecimal("30");
        if (quantidade >= 100) return new BigDecimal("20");
        if (quantidade >= 50) return new BigDecimal("10");
        return BigDecimal.ZERO;
    }

    private String gerarMensagemSimulacao(Integer posicaoAtual, Integer posicaoNova, Integer pontos) {
        if (posicaoNova < posicaoAtual) {
            int melhoria = posicaoAtual - posicaoNova;
            return String.format("Você subirá %d posição(ões), da %dª para a %dª posição",
                    melhoria, posicaoAtual, posicaoNova);
        } else if (posicaoNova.equals(posicaoAtual)) {
            return String.format("Você manterá a %dª posição", posicaoAtual);
        } else {
            return String.format("Você ficará na %dª posição com %d pontos adicionais",
                    posicaoNova, pontos);
        }
    }

    private String gerarTransacaoId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void adicionarPontosAoFreteiro(HistoricoCompraPontos historico) {
        PontosRanking pontos = pontosRankingRepository
                .findByFreteiroIdAndCidade(historico.getFreteiro().getId(), historico.getCidade())
                .orElse(PontosRanking.builder()
                        .freteiro(historico.getFreteiro())
                        .cidade(historico.getCidade())
                        .pontosAtivos(0)
                        .pontosGastos(0)
                        .totalPontosComprados(0)
                        .build());

        pontos.adicionarPontos(historico.getQuantidadePontos(), DIAS_VALIDADE_PONTOS);
        pontosRankingRepository.save(pontos);
    }

    private List<HistoricoCompraResponse> buscarUltimasCompras(Long freteiroId, String cidade, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<HistoricoCompraPontos> page = cidade != null
                ? historicoRepository.findByFreteiroIdAndCidade(freteiroId, cidade, pageable)
                : historicoRepository.findByFreteiroIdOrderByCreatedAtDesc(freteiroId, pageable);

        return page.getContent().stream()
                .map(this::toHistoricoResponse)
                .collect(Collectors.toList());
    }

    private HistoricoCompraResponse toHistoricoResponse(HistoricoCompraPontos h) {
        return HistoricoCompraResponse.builder()
                .id(h.getId())
                .transacaoId(h.getTransacaoId())
                .cidade(h.getCidade())
                .quantidadePontos(h.getQuantidadePontos())
                .valorTotal(h.getValorTotal())
                .descontoPercentual(h.getDescontoPercentual())
                .metodoPagamento(h.getMetodoPagamento())
                .status(h.getStatus())
                .posicaoEstimadaAntes(h.getPosicaoEstimadaAntes())
                .posicaoEstimadaDepois(h.getPosicaoEstimadaDepois())
                .dataPagamento(h.getDataPagamento())
                .dataExpiracao(h.getDataExpiracao())
                .createdAt(h.getCreatedAt())
                .build();
    }
}
