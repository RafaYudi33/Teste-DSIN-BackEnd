package org.rafs.tstedsin.Handle;

import org.rafs.tstedsin.Errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final CustomResponseError handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new CustomResponseError("Validation error", LocalDateTime.now(), request.getDescription(false), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientNotFoundException.class)
    public final CustomResponseError handleClientNotFoundException(ClientNotFoundException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppointmentNotFoundException.class)
    public final CustomResponseError handleAppointmentNotFoundException(AppointmentNotFoundException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppointmentModificationRestrictedException.class)
    public final CustomResponseError handleAppointmentAppointmentModificationRestrictedException(
            AppointmentModificationRestrictedException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));

    }

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
