package org.rafs.tstedsin.Errors;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CustomResponseError {

    private String message;
    private LocalDateTime timestamp;
    private String details;
    private Map<String, String> errors;

    public CustomResponseError(String message, LocalDateTime timestamp, String details) {
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    public CustomResponseError(String message, LocalDateTime timestamp, String details, Map<String, String> errors) {
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
        this.errors = errors;
    }

}
