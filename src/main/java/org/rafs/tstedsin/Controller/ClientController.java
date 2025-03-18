package org.rafs.tstedsin.Controller;

import jakarta.validation.Valid;
import org.rafs.tstedsin.Model.Admin;
import org.rafs.tstedsin.Model.Client;
import org.rafs.tstedsin.Model.User;
import org.rafs.tstedsin.Repository.AdminRepository;
import org.rafs.tstedsin.Service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("")
    public ResponseEntity<User> createClient(@Valid @RequestBody Client user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createUserClient(user));
    }

}
