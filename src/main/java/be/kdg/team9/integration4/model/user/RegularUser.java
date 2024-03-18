package be.kdg.team9.integration4.model.user;

import be.kdg.team9.integration4.model.Survey;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "application_user")
public class RegularUser extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private List<Survey> surveySubmitted;

    public RegularUser(long id, String firstName, String lastName, String email, String password, List<Survey> surveySubmitted) {
        super(firstName, lastName, email, password);
        this.id = id;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
