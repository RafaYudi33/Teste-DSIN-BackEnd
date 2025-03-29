package org.rafs.tstedsin.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.rafs.tstedsin.Enum.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String username;


    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false, nullable = false)
    private Role role;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ROLE_ADMIN) return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENT")
        );
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
