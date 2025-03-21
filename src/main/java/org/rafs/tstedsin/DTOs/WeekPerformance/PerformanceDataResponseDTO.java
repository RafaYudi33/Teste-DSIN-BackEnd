package org.rafs.tstedsin.DTOs.WeekPerformance;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PerformanceDataResponseDTO(@JsonProperty("Faturamento total") BigDecimal totalRevenue,
                                         @JsonProperty("Média de faturamento diário") BigDecimal averageDailyRevenue,
                                         @JsonProperty("Total de agendamentos concluídos") Long totalAppointmentsCompleted,
                                         @JsonProperty("Serviço mais lucrativo") String topRevenueService,
                                         @JsonProperty("Duração média dos agendamentos (min)") BigDecimal averageAppointmentDuration) {
}
