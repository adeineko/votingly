package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class NewOpenAnswer {
    @NotBlank
    private String answer;

    private long surveyId;
    private long userId;
    private Question questionId;

    public NewOpenAnswer() {
    }

    public NewOpenAnswer(String answer, long surveyId, long userId, Question questionId) {
        this.answer = answer;
        this.surveyId = surveyId;
        this.userId = userId;
        this.questionId = questionId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }
}
