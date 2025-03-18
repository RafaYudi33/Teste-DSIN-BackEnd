package org.rafs.tstedsin.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@DiscriminatorValue("ROLE_ADMIN")
@Entity
public class Admin extends User {

    public Admin(){

    }
}
