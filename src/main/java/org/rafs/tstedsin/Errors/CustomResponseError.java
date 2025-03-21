package org.rafs.tstedsin.Errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class CustomResponseError {

    private String message;
    private LocalDateTime timestamp;
    private String details;


    public CustomResponseError(String message, LocalDateTime timestamp, String details) {
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }



}
