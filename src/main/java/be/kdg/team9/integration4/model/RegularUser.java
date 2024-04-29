package be.kdg.team9.integration4.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("REGULAR")
public class RegularUser extends User {

    @Transient
    private List<Survey> surveySubmitted;

    public RegularUser(long id, String firstName, String lastName, String email, String password, List<Survey> surveySubmitted) {
        super(id, firstName, lastName, email, password);
        this.surveySubmitted = surveySubmitted;
    }

    public RegularUser() {
    }

    public List<Survey> getSurveySubmitted() {
        return surveySubmitted;
    }

    public void setSurveySubmitted(List<Survey> surveySubmitted) {
        this.surveySubmitted = surveySubmitted;
    }
}
