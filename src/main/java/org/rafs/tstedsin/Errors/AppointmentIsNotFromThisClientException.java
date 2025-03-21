package org.rafs.tstedsin.Errors;

public class AppointmentIsNotFromThisClientException extends RuntimeException {
    public AppointmentIsNotFromThisClientException() {
        super("O agendamento não pertence a este cliente");
    }
}
