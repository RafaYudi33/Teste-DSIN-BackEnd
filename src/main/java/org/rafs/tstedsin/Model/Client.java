package org.rafs.tstedsin.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rafs.tstedsin.Enum.Role;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data

@DiscriminatorValue("ROLE_CLIENT")
@Entity
public class Client extends User{

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;

    public Client(){
        this.setRole(Role.ROLE_CLIENT);
    }

    public Client(String username, String password, String name, List<Appointment> appointments) {
        super(username, password, name);
        this.setRole(Role.ROLE_CLIENT);
        this.appointments = appointments;
    }

}
