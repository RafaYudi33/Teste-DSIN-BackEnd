package org.rafs.tstedsin.Service;

import org.rafs.tstedsin.DTOs.Appointment.AdmUpdateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.CreateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.UpdateAppointmentRequestDTO;
import org.rafs.tstedsin.Enum.AppointmentStatus;
import org.rafs.tstedsin.Errors.AppointmentIsNotFromThisClientException;
import org.rafs.tstedsin.Errors.AppointmentModificationRestrictedException;
import org.rafs.tstedsin.Errors.AppointmentNotFoundException;
import org.rafs.tstedsin.Errors.ClientNotFoundException;
import org.rafs.tstedsin.Mapper.AppointmentMapper;
import org.rafs.tstedsin.Model.Appointment;
import org.rafs.tstedsin.Model.BeautyService;
import org.rafs.tstedsin.Model.Client;
import org.rafs.tstedsin.Repository.AppointmentRepository;
import org.rafs.tstedsin.Repository.BeautyServiceRepository;
import org.rafs.tstedsin.Repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final BeautyServiceRepository beautyServiceRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository, BeautyServiceRepository beautyServiceRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.beautyServiceRepository = beautyServiceRepository;
        this.appointmentMapper = appointmentMapper;
    }

    public List<Appointment> findAllByClientId(Long id){
        return appointmentRepository.findAllByClientId(id);
    }

    public Appointment createAppointment(CreateAppointmentRequestDTO appointmentDto){

        Client client = clientRepository.findById(appointmentDto.clientId()).orElseThrow(
                ClientNotFoundException::new
        );
        List<BeautyService> beautyServices = beautyServiceRepository.findAllById(appointmentDto.beautyServicesIds());

        return appointmentRepository.save(
                appointmentMapper.toModel(appointmentDto, client, appointmentDto.dateTime(), beautyServices)
        );
    }

    public void admUpdateAppointment(AdmUpdateAppointmentRequestDTO appointmentDto){
        Appointment appointment = appointmentRepository.findById(appointmentDto.id()).orElseThrow(
                AppointmentNotFoundException::new
        );


        List<BeautyService> newBeautyServices = beautyServiceRepository.findAllById(appointmentDto.beautyServicesIds());

        appointment.setBeautyServices(newBeautyServices);
        appointment.setDateTime(appointmentDto.dateTime());
        appointment.setStatus(appointmentDto.status());
        appointmentRepository.save(appointment);
    }

    public void updateAppointment(UpdateAppointmentRequestDTO appointmentDto){

        Appointment appointment = appointmentRepository.findById(appointmentDto.id()).orElseThrow(
                AppointmentNotFoundException::new
        );

        if (!Objects.equals(appointmentDto.idClient(), appointment.getClient().getId())){
            throw new AppointmentIsNotFromThisClientException();
        }

        LocalDateTime twoDaysBeforeAppointment = appointment.getDateTime().minusDays(2);

        if (LocalDateTime.now().isAfter(twoDaysBeforeAppointment)) {
            throw new AppointmentModificationRestrictedException();
        }
        List<BeautyService> newBeautyServices = beautyServiceRepository.findAllById(appointmentDto.beautyServicesIds());

        appointment.setBeautyServices(newBeautyServices);
        appointment.setDateTime(appointmentDto.dateTime());
        appointment.setStatus(AppointmentStatus.PENDENTE);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public void confirmAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                AppointmentNotFoundException::new
        );

        appointment.setStatus(AppointmentStatus.CONFIRMADO);
        appointmentRepository.save(appointment);
    }

}
