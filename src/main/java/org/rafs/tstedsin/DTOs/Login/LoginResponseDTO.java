package org.rafs.tstedsin.DTOs.Login;

import org.rafs.tstedsin.Enum.Role;

public record LoginResponseDTO(String token,
                               long expirationTime,
                               Role role,
                               Long idUser,
                               String name,
                               String username
) {
}
