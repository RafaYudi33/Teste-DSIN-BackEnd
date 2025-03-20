package org.rafs.tstedsin.Errors;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException() {
        super("Agendamento não encontrado");
    }
}
