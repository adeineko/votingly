package be.kdg.team9.integration4.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ORG_ADMIN")
public class OrgAdmin extends User{
    @Column
    private long orgId;

    public OrgAdmin(long id, String firstName, String lastName, String email, String password, long orgId) {
        super(id, firstName, lastName, email, password);
        this.orgId = orgId;
    }

    public OrgAdmin(long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password);
    }

    public OrgAdmin() {
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
