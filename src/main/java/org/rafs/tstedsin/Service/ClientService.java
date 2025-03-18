package org.rafs.tstedsin.Service;

import org.rafs.tstedsin.DTOs.Login.LoginRequestDTO;
import org.rafs.tstedsin.DTOs.Login.LoginResponseDTO;
import org.rafs.tstedsin.Enum.Role;
import org.rafs.tstedsin.Model.User;
import org.springframework.security.core.GrantedAuthority;

import org.rafs.tstedsin.Errors.UnauthorizedException;
import org.rafs.tstedsin.Errors.UserAlreadyExistsException;
import org.rafs.tstedsin.Model.Client;
import org.rafs.tstedsin.Repository.ClientRepository;
import org.rafs.tstedsin.Security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Client createUserClient(Client user) {

        if(clientRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException();
        }
        System.out.println(user.getRole());
        String passEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passEncoded);
        return clientRepository.save(user);
    }

}
