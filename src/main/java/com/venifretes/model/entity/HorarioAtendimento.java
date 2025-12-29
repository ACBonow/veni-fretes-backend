package com.venifretes.model.entity;

import com.venifretes.model.enums.DiaDaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_atendimento", indexes = {
    @Index(name = "idx_horarios_freteiro", columnList = "freteiro_id"),
    @Index(name = "idx_horarios_dia", columnList = "dia_semana")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 20)
    private DiaDaSemana diaSemana;

    @Column(name = "hora_inicio", nullable = false, length = 5)
    private String horaInicio; // Format: "HH:MM"

    @Column(name = "hora_fim", nullable = false, length = 5)
    private String horaFim; // Format: "HH:MM"

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Validates that time is in 15-minute increments
     * Valid: "08:00", "14:15", "18:30", "23:45"
     * Invalid: "08:10", "25:00", "12:50"
     */
    public static boolean isValidTimeSlot(String time) {
        if (time == null || !time.matches("^([0-1][0-9]|2[0-3]):(00|15|30|45)$")) {
            return false;
        }
        return true;
    }

    /**
     * Converts time string to LocalTime for comparisons
     */
    public LocalTime getHoraInicioAsLocalTime() {
        return LocalTime.parse(horaInicio);
    }

    public LocalTime getHoraFimAsLocalTime() {
        return LocalTime.parse(horaFim);
    }

    /**
     * Validates that end time is after start time
     */
    public boolean isValidTimeRange() {
        return getHoraFimAsLocalTime().isAfter(getHoraInicioAsLocalTime());
    }
}
