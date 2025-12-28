package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface FreteiroRepositoryCustom {
    Page<Freteiro> findFreteirosRanqueadosCustom(String cidade, String estado, BigDecimal avaliacaoMinima, Pageable pageable);
}
