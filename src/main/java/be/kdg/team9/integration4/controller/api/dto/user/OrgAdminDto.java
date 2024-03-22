package be.kdg.team9.integration4.controller.api.dto.user;

public class OrgAdminDto extends UserDto {
    private long orgId;

    public OrgAdminDto(long id, String firstName, String lastName, String email, long orgId) {
        super(id, firstName, lastName, email);
        this.orgId = orgId;
    }

    public OrgAdminDto() {
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
