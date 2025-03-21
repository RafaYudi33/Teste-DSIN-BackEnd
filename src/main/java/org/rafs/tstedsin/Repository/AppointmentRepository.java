package org.rafs.tstedsin.Repository;

import org.rafs.tstedsin.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByClientId(Long clientId);

    @Query("SELECT COALESCE(SUM(bs.price), 0.0) FROM Appointment a " +
            "JOIN a.beautyServices bs " +
            "WHERE a.dateTime >= :startDate " +
            "AND a.dateTime <= :endDate " +
            "AND a.status = 'CONFIRMADO'")
    Double getTotalRevenueForLastWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query("SELECT COUNT(a) FROM Appointment a " +
            "WHERE a.dateTime BETWEEN :startDate AND :endDate " +
            "AND a.status = 'CONFIRMADO'")
    Long countAppointmentsCompletedLastWeek(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT bs.name FROM Appointment a " +
            "JOIN a.beautyServices bs " +
            "WHERE a.dateTime >= :startDate " +
            "AND a.dateTime <= :endDate " +
            "AND a.status = 'CONFIRMADO' " +
            "GROUP BY bs.name " +
            "ORDER BY SUM(bs.price) DESC LIMIT 1")
    String getTopRevenueService(@Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(bs.durationMinutes), 0) FROM Appointment a " +
            "JOIN a.beautyServices bs " +
            "WHERE a.dateTime >= :startDate " +
            "AND a.dateTime <= :endDate " +
            "AND a.status = 'CONFIRMADO'")
    Long getTotalServiceDuration(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

}
