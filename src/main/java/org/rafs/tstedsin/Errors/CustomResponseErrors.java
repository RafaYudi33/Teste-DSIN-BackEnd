package org.rafs.tstedsin.Errors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CustomResponseErrors extends CustomResponseError{
    private Map<String, String> errors;

    public CustomResponseErrors(Map<String, String> errors, String message, LocalDateTime timestamp, String details) {
        super(message, timestamp, details);
        this.errors = errors;
    }
}
