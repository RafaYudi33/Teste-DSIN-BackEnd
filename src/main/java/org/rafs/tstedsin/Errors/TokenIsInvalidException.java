package org.rafs.tstedsin.Errors;

public class TokenIsInvalidException extends RuntimeException {

    public TokenIsInvalidException(){
        super("Token Inválido");
    }

}
