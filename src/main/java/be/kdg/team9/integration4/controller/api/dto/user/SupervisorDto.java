package be.kdg.team9.integration4.controller.api.dto.user;


public class SupervisorDto extends UserDto {
    private String notesTaken;

    public SupervisorDto(long id, String firstName, String lastName, String email, String password, String userType, String notesTaken) {
        super(id, firstName, lastName, email, password, userType);
        this.notesTaken = notesTaken;
    }

    public SupervisorDto() {
    }

    public String getNotesTaken() {
        return notesTaken;
    }

    public void setNotesTaken(String notesTaken) {
        this.notesTaken = notesTaken;
    }

    public String getUserType() {
        return "SUPERVISOR";
    }

    public void setUserType(String userType) {
        if (!"SUPERVISOR".equals(userType)) {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}
