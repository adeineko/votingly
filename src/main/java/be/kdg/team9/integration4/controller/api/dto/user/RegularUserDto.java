package be.kdg.team9.integration4.controller.api.dto.user;

import be.kdg.team9.integration4.model.Survey;
import jakarta.persistence.Transient;

import java.util.List;

public class RegularUserDto extends UserDto {

    @Transient
    private List<Survey> surveySubmitted;

    public RegularUserDto(long id, String firstName, String lastName, String email, List<Survey> surveySubmitted) {
        super(id, firstName, lastName, email);
        this.surveySubmitted = surveySubmitted;
    }

    public RegularUserDto() {
    }

    public List<Survey> getSurveySubmitted() {
        return surveySubmitted;
    }

    public void setSurveySubmitted(List<Survey> surveySubmitted) {
        this.surveySubmitted = surveySubmitted;
    }
}
