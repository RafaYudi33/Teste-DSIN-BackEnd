package org.rafs.tstedsin.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class AppointmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private LocalDateTime modificationDate;

    private String description;

    public AppointmentHistory(Appointment appointment, LocalDateTime modificationDate, String description) {
        this.appointment = appointment;
        this.modificationDate = modificationDate;
        this.description = description;
    }
}
