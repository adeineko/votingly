package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class NewOpenAnswer {
    @NotBlank
    private String answer;
    @NotBlank
    private long surveyId;
    // private long userId;

    public NewOpenAnswer() {
    }

    public NewOpenAnswer(@NotBlank String answer, @NotBlank long surveyId) {
        this.answer = answer;
        this.surveyId = surveyId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }
}
