package org.rafs.tstedsin.Errors;

public class AppointmentModificationRestrictedException extends RuntimeException {
    public AppointmentModificationRestrictedException() {
        super("Modificações só são permitidas em mais de 2 dias do agendamento");
    }
}
