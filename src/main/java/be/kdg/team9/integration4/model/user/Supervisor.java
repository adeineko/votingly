package be.kdg.team9.integration4.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERVISOR")
public class Supervisor extends User {

    public Supervisor(long id, String firstName, String lastName, String email, String password, String userType) {
        super(id, firstName, lastName, email, password, userType);
    }

    public Supervisor() {
    }

    @Override
    public String getUserType() {
        return "SUPERVISOR";
    }

    @Override
    public void setUserType(String userType) {
        if (!"SUPERVISOR".equals(userType)) {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}
