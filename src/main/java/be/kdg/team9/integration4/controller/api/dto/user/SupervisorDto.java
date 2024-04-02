package be.kdg.team9.integration4.controller.api.dto.user;


public class SupervisorDto extends UserDto {
    private String notesTaken;
    private long orgId;

    public SupervisorDto(long id, String firstName, String lastName, String email, String notesTaken, long orgId) {
        super(id, firstName, lastName, email);
        this.notesTaken = notesTaken;
        this.orgId = orgId;
    }

    public SupervisorDto() {
    }

    public String getNotesTaken() {
        return notesTaken;
    }

    public void setNotesTaken(String notesTaken) {
        this.notesTaken = notesTaken;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
