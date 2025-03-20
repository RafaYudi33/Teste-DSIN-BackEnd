package org.rafs.tstedsin.Errors;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Cliente não foi encontrado");
    }
}
