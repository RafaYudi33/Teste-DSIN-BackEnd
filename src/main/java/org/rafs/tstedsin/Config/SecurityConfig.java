package org.rafs.tstedsin.Config;


import jakarta.servlet.Filter;
import org.rafs.tstedsin.Security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "api/client").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/appointment").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST,"api/appointment").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT,"api/appointment").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET,"api/appointment/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/appointment/confirm").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/appointment/full-update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/reports/last-week").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/beauty-services").hasRole("CLIENT")

                        .anyRequest().hasRole("CLIENT")
                )
                .cors(cors -> {})
                .build();
    }
}
