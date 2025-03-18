package org.rafs.tstedsin.DTOs.Login;

import org.rafs.tstedsin.Enum.Role;

import java.time.LocalDateTime;

public record LoginResponseDTO(String token, LocalDateTime expirationTime, Role role) {
}
