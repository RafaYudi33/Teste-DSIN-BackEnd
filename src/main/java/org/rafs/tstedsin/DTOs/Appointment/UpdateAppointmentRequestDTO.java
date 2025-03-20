package org.rafs.tstedsin.DTOs.Appointment;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateAppointmentRequestDTO(Long id, List<Long> beautyServicesIds,
                                          @FutureOrPresent(message = "Não é permitido datas passadas") LocalDateTime dateTime) {
}
