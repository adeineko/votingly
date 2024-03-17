package be.kdg.team9.integration4.model.user;

public class Supervisor extends User{
    private String notesTaken;
    private long orgId;

    public Supervisor(String firstName, String lastName, String email, String password, String notesTaken, long orgId) {
        super(firstName, lastName, email, password);
        this.notesTaken = notesTaken;
        this.orgId = orgId;
    }

    public Supervisor() {
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
