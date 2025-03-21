package org.rafs.tstedsin.Service;

import org.rafs.tstedsin.DTOs.WeekPerformance.PerformanceDataResponseDTO;
import org.rafs.tstedsin.Repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class WeekPerformanceService {

    private final AppointmentRepository appointmentRepository;


    public WeekPerformanceService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public PerformanceDataResponseDTO weekPerformanceReport(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minusDays(7);


        BigDecimal totalRevenue = BigDecimal.valueOf(appointmentRepository.getTotalRevenueForLastWeek(startTime, now))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal meanRevenuePerDay = totalRevenue.divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_UP);

        Long completedAppointmentsCount = appointmentRepository.countAppointmentsCompletedLastWeek(startTime, now);
        String topRevenueService = appointmentRepository.getTopRevenueService(startTime, now);

        BigDecimal averageAppointmentDuration = BigDecimal.ZERO;
        if (completedAppointmentsCount > 0) {
            BigDecimal totalServiceDuration = BigDecimal.valueOf(appointmentRepository.getTotalServiceDuration(startTime, now));
            averageAppointmentDuration = totalServiceDuration.divide(BigDecimal.valueOf(completedAppointmentsCount), 0, RoundingMode.HALF_UP);
        }

        return new PerformanceDataResponseDTO(totalRevenue,
                meanRevenuePerDay,
                completedAppointmentsCount,
                topRevenueService,
                averageAppointmentDuration);
    }
}
