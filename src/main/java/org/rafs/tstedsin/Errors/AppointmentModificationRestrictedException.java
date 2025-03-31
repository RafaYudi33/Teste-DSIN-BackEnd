package org.rafs.tstedsin.Errors;

public class AppointmentModificationRestrictedException extends RuntimeException {
    public AppointmentModificationRestrictedException() {
        super("Só é possivel editar agendamentos com pelo menos 2 dias de antecedência!");
    }
}
