package org.rafs.tstedsin.Model;



import jakarta.persistence.*;
import lombok.*;
import org.rafs.tstedsin.Enum.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "appointment_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<BeautyService> beautyServices;


    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public Appointment() {
        this.status = AppointmentStatus.PENDENTE;
    }

    public Appointment(Client client, List<BeautyService> beautyServices, LocalDateTime dateTime) {
        this.client = client;
        this.beautyServices = beautyServices;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDENTE;
    }

}
