package org.rafs.tstedsin.Handle;

import org.rafs.tstedsin.Errors.CustomResponseError;
import org.rafs.tstedsin.Errors.TokenIsInvalidException;
import org.rafs.tstedsin.Errors.UnauthorizedException;
import org.rafs.tstedsin.Errors.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public CustomResponseError handleValidationExceptions(MethodArgumentNotValidException e, WebRequest request) {
//        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
//    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public final CustomResponseError handleUnauthorizedException(UnauthorizedException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenIsInvalidException.class)
    public final CustomResponseError handleTokenIsInvalidException(UnauthorizedException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public final CustomResponseError handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
    }
}
