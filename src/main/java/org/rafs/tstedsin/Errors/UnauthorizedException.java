package org.rafs.tstedsin.Errors;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Usuário e/ou senha incorreto(s)");
    }
}
