package org.rafs.tstedsin.Mapper;

import org.rafs.tstedsin.DTOs.Appointment.CreateAppointmentRequestDTO;
import org.rafs.tstedsin.Model.Appointment;
import org.rafs.tstedsin.Model.BeautyService;
import org.rafs.tstedsin.Model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentMapper {

    public Appointment toModel(CreateAppointmentRequestDTO appointmentDto,
                               Client client,
                               LocalDateTime dateTime,
                               List<BeautyService> beautyServices) {
        return new Appointment(client, beautyServices, appointmentDto.dateTime());
    }
}
