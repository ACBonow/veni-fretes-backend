package com.venifretes.repository;

import com.venifretes.model.entity.Contratante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratanteRepository extends JpaRepository<Contratante, Long> {

    // People management - advanced filtering
    // Note: Contratante extends Pessoa (not Usuario), so it doesn't have 'ativo' field
    Page<Contratante> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
