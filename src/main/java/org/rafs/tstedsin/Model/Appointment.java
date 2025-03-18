package org.rafs.tstedsin.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rafs.tstedsin.Enum.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "appointment_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<BeautyService> beautyServices;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<AppointmentHistory> history;

    public Appointment(Client client, List<BeautyService> beautyServices, LocalDateTime dateTime, AppointmentStatus status) {
        this.client = client;
        this.beautyServices = beautyServices;
        this.dateTime = dateTime;
        this.status = status;
    }
}
