package org.rafs.tstedsin.DTOs.Appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateAppointmentRequestDTO(@NotNull Long id, List<Long> beautyServicesIds,
                                          @FutureOrPresent(message = "Não são permitidas datas passadas") LocalDateTime dateTime,
                                          @NotNull Long idClient) {
}
