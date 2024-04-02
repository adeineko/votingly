package be.kdg.team9.integration4.controller.api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AnswerDto {
    private long answerId;
    private long surveyId;
    private long userId;
    private String answer;

    public AnswerDto() {
    }

    public AnswerDto(long answerId, long surveyId, long userId, String answer) {
        this.answerId = answerId;
        this.surveyId = surveyId;
        this.userId = userId;
        this.answer = answer;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
