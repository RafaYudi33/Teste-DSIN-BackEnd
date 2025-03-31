package org.rafs.tstedsin.DTOs.Appointment;

import jakarta.validation.constraints.FutureOrPresent;
import org.rafs.tstedsin.Enum.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AdmUpdateAppointmentRequestDTO(Long id, List<Long> beautyServicesIds,
                                             @FutureOrPresent(message = "Não são permitidas datas passadas")
                                             LocalDateTime dateTime,
                                             AppointmentStatus status) {
}
