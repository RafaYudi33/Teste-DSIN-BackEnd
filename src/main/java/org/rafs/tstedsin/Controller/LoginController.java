package org.rafs.tstedsin.Controller;

import jakarta.validation.Valid;
import org.rafs.tstedsin.DTOs.Login.LoginRequestDTO;
import org.rafs.tstedsin.DTOs.Login.LoginResponseDTO;
import org.rafs.tstedsin.Service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/login")
@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(loginService.login(loginRequest));
    }
}
