package be.kdg.team9.integration4.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("P_ADMIN")
public class PlatformAdmin extends User {
    public PlatformAdmin() {
    }

    public PlatformAdmin(String firstName, String lastName, String email, String password, long id) {
        super(id, firstName, lastName, email, password);
    }

    @Override
    public String getUserType() {
        return "P_ADMIN";
    }
}
