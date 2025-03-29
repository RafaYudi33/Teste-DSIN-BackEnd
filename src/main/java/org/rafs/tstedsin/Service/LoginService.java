package org.rafs.tstedsin.Service;

import org.rafs.tstedsin.DTOs.Login.LoginRequestDTO;
import org.rafs.tstedsin.DTOs.Login.LoginResponseDTO;
import org.rafs.tstedsin.Enum.Role;
import org.rafs.tstedsin.Errors.UnauthorizedException;
import org.rafs.tstedsin.Model.User;
import org.rafs.tstedsin.Security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {


    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Value("${security.jwt.expirationInMillisecond}")
    private long expirationInMillisecond;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    public LoginResponseDTO login(LoginRequestDTO credentials) {
        try {
            UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(
                    credentials.username(), credentials.password());

            Authentication auth = this.authenticationManager.authenticate(userPassAuth);

            User user = (User)auth.getPrincipal();
            String token = this.tokenService.generateToken(user);

            String roleStr = auth.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");



            return new LoginResponseDTO(
                    token,
                    Instant.now()
                            .plusMillis(expirationInMillisecond)
                            .getEpochSecond(),
                    Role.valueOf(roleStr),
                    user.getId(),
                    user.getName(),
                    user.getUsername()
            );

        }catch (Exception exception){
            throw new UnauthorizedException();
        }
    }
}
