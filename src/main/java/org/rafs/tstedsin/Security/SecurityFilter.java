package org.rafs.tstedsin.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rafs.tstedsin.Repository.AdminRepository;
import org.rafs.tstedsin.Repository.ClientRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final TokenService tokenService;

    public SecurityFilter(AdminRepository adminRepository, ClientRepository clientRepository, TokenService tokenService) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token!= null){
            var username = tokenService.validateToken(token);
            UserDetails user = adminRepository.findByUsername(username);
            if(user == null){
               user = clientRepository.findByUsername(username);
            }

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null) return token.replace("Bearer ","");
        return null;
    }
}
