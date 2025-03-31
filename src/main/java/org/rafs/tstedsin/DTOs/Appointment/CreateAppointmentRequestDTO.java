package org.rafs.tstedsin.DTOs.Appointment;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;
import java.util.List;

public record CreateAppointmentRequestDTO(Long clientId,
                                          List<Long> beautyServicesIds,
                                          @FutureOrPresent(message = "Não são permitidas datas passadas") LocalDateTime dateTime) {
}
