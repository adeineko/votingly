package be.kdg.team9.integration4.model.user;

public class PlatformAdmin extends User{
    private long id;
    public PlatformAdmin() {
    }

    public PlatformAdmin(String firstName, String lastName, String email, String password, long id) {
        super(firstName, lastName, email, password);
        this.id = id;
    }
}
