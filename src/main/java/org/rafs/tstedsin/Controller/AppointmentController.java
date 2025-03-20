package org.rafs.tstedsin.Controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.rafs.tstedsin.DTOs.Appointment.AdmUpdateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.CreateAppointmentRequestDTO;
import org.rafs.tstedsin.DTOs.Appointment.UpdateAppointmentRequestDTO;
import org.rafs.tstedsin.Model.Appointment;
import org.rafs.tstedsin.Service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAllByClientId(@PathParam("id") Long id) {
        return ResponseEntity.ok().body(appointmentService.findAllByClientId(id));
    }

    @PostMapping
    public ResponseEntity<Appointment> save(@Valid @RequestBody CreateAppointmentRequestDTO appointmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointmentDto));
    }

    @PutMapping
    public ResponseEntity<Void> clientUpdate(@Valid @RequestBody UpdateAppointmentRequestDTO appointmentDto) {
        appointmentService.updateAppointment(appointmentDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("full-update")
    public ResponseEntity<Void> admUpdateAppointment(@Valid @RequestBody AdmUpdateAppointmentRequestDTO appointmentDto) {
        appointmentService.admUpdateAppointment(appointmentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> findAll(){
        return ResponseEntity.ok().body(appointmentService.findAll());
    }

    @PutMapping("/confirm") ResponseEntity<Void> confirm(@RequestParam("id") Long id) {
        appointmentService.confirmAppointment(id);
        return ResponseEntity.ok().build();
    }
}
