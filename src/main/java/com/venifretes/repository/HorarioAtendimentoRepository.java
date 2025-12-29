package com.venifretes.repository;

import com.venifretes.model.entity.Freteiro;
import com.venifretes.model.entity.HorarioAtendimento;
import com.venifretes.model.enums.DiaDaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento, Long> {

    List<HorarioAtendimento> findByFreteiro(Freteiro freteiro);

    List<HorarioAtendimento> findByFreteiroAndDiaSemana(Freteiro freteiro, DiaDaSemana diaSemana);

    void deleteByFreteiro(Freteiro freteiro);

    @Query("SELECT DISTINCT h.freteiro FROM HorarioAtendimento h WHERE h.diaSemana = :dia")
    List<Freteiro> findFreteirosWorkingOn(@Param("dia") DiaDaSemana dia);
}
