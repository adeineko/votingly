package be.kdg.team9.integration4.controller.api.dto.answer;

import be.kdg.team9.integration4.model.Question;

public class OpenAnswerDto {
    private long answerId;
    private long surveyId;
    private long userId;
    private Question questionId;
    private String answer;

    public OpenAnswerDto() {
    }

    public OpenAnswerDto(long answerId, long surveyId, long userId, Question questionId, String answer) {
        this.answerId = answerId;
        this.surveyId = surveyId;
        this.userId = userId;
        this.questionId = questionId;
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

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
