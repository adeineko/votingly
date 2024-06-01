package be.kdg.team9.integration4.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERVISOR")
public class Supervisor extends User {

    @Column(name = "notes_taken")
    private String notesTaken;

    public Supervisor(long id, String firstName, String lastName, String email, String password, String userType, String notesTaken) {
        super(id, firstName, lastName, email, password, userType);
        this.notesTaken = notesTaken;
    }

    public Supervisor() {
    }

    public String getNotesTaken() {
        return notesTaken;
    }

    public void setNotesTaken(String notesTaken) {
        this.notesTaken = notesTaken;
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
