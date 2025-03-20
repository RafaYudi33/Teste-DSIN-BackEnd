package org.rafs.tstedsin.Security;

import org.rafs.tstedsin.Model.User;
import org.rafs.tstedsin.Repository.AdminRepository;
import org.rafs.tstedsin.Repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    public CustomUserDetailsService(AdminRepository adminRepository, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = adminRepository.findByUsername(username);

        if (user == null) {
            user = clientRepository.findByUsername(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return user;
    }
}
