package org.rafs.tstedsin.unnitests.Service;



import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.rafs.tstedsin.DTOs.Appointment.AdmUpdateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.CreateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.UpdateAppointmentRequestDTO;
import org.rafs.tstedsin.Enum.AppointmentStatus;
import org.rafs.tstedsin.Errors.AppointmentNotFoundException;
import org.rafs.tstedsin.Errors.ClientNotFoundException;
import org.rafs.tstedsin.Mapper.AppointmentMapper;
import org.rafs.tstedsin.Model.Appointment;
import org.rafs.tstedsin.Model.BeautyService;
import org.rafs.tstedsin.Model.Client;
import org.rafs.tstedsin.Repository.AppointmentRepository;
import org.rafs.tstedsin.Repository.BeautyServiceRepository;
import org.rafs.tstedsin.Repository.ClientRepository;
import org.rafs.tstedsin.Service.AppointmentService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BeautyServiceRepository beautyServiceRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private Client client;
    private BeautyService beautyService;
    private Appointment appointment;
    private CreateAppointmentRequestDTO createAppointmentRequestDto;
    private AdmUpdateAppointmentRequestDTO admUpdateAppointmentRequestDTO;
    private UpdateAppointmentRequestDTO updateAppointmentRequestDTO;

    @BeforeEach
    public void setUp() {
        client = new Client();
        beautyService = new BeautyService();
        appointment = new Appointment();
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        admUpdateAppointmentRequestDTO = new AdmUpdateAppointmentRequestDTO(
                1L,
                List.of(1L),
                futureDateTime,
                AppointmentStatus.CANCELADO
        );
        updateAppointmentRequestDTO = new UpdateAppointmentRequestDTO(
                1L,
                List.of(1L),
                futureDateTime,
                1L
        );

        createAppointmentRequestDto = new CreateAppointmentRequestDTO(1L, List.of(1L), futureDateTime);

    }

    @Test
    public void testCreateAppointmentSuccess() {

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(appointmentMapper.toModel(any(), any(), any())).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment createdAppointment = appointmentService.createAppointment(createAppointmentRequestDto);

        assertNotNull(createdAppointment);
        verify(clientRepository).findById(createAppointmentRequestDto.clientId());
        verify(beautyServiceRepository).findAllById(createAppointmentRequestDto.beautyServicesIds());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    public void testCreateAppointmentWithNonExistentClient() {
        when(clientRepository.findById(createAppointmentRequestDto.clientId())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            appointmentService.createAppointment(createAppointmentRequestDto);
        });
    }

    @Test
    public void testAdmUpdateAppointmentSuccess() {


        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        when(beautyServiceRepository.findAllById(anyList())).thenReturn(List.of(beautyService));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        appointmentService.admUpdateAppointment(admUpdateAppointmentRequestDTO);


        verify(appointmentRepository).findById(1L);
        verify(beautyServiceRepository).findAllById(List.of(1L));
        verify(appointmentRepository).save(appointment);


    }

    @Test
    public void testUpdateAppointmentSuccess(){
        Client client = new Client();
        client.setId(1L);
        Appointment appointmentToUpdate = new Appointment(client, List.of(beautyService), LocalDateTime.now().plusDays(3L));

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointmentToUpdate));
        when(beautyServiceRepository.findAllById(anyList())).thenReturn(List.of(beautyService));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointmentToUpdate);

        appointmentService.updateAppointment(updateAppointmentRequestDTO);

        verify(appointmentRepository).findById(1L);
        verify(beautyServiceRepository).findAllById(List.of(1L));
        verify(appointmentRepository).save(appointmentToUpdate);
    }

    @Test
    public void testFindAllByClientIdSuccess() {
        Long clientId = 1L;
        List<Appointment> expectedAppointments = List.of(new Appointment(), new Appointment());

        when(appointmentRepository.findAllByClientId(clientId)).thenReturn(expectedAppointments);
        List<Appointment> actualAppointments = appointmentService.findAllByClientId(clientId);

        assertNotNull(actualAppointments);
        assertEquals(expectedAppointments, actualAppointments);
        verify(appointmentRepository).findAllByClientId(clientId);
    }

    @Test
    public void testConfirmAppointmentSuccess(){
        Long appointmentId = 1L;
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        appointmentService.confirmAppointment(1L);

        assertEquals(AppointmentStatus.CONFIRMADO, appointment.getStatus());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    public void testConfirmAppointmentWithNonExistentAppointment() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.confirmAppointment(1L);
        });
    }

    @Test
    public void testFindAllAppointmentsSuccess() {
        List<Appointment> expectedAppointments = List.of(new Appointment(), new Appointment());
        when(appointmentRepository.findAll()).thenReturn(expectedAppointments);

        List<Appointment> result = appointmentService.findAll();
        assertNotNull(result);
        verify(appointmentRepository).findAll();
    }

}
