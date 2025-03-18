package org.rafs.tstedsin.Errors;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Nome de usuário indisponível");
    }
}
