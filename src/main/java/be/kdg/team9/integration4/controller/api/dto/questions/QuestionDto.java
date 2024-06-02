package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.QuestionType;


public class QuestionDto {
    private long id;

    private String questionName;

    private QuestionType questionType;

    private long surveyId;


    public QuestionDto() {
    }

    public QuestionDto(String questionName, QuestionType questionType) {
        this.questionName = questionName;
        this.questionType = questionType;
    }

    public QuestionDto(long id, String questionName, QuestionType questionType, long surveyId) {
        this.id = id;
        this.questionName = questionName;
        this.questionType = questionType;
        this.surveyId = surveyId;
    }

//    public QuestionDto(Question question) {
//        this.id = question.getId();
//        this.questionName = question.getQuestionName();
//        this.questionType = question.getQuestionType();
//        this.surveyId = question.getSurvey();
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public String toString() {
        return "QuestionDto [id=" + id + ", questionName=" + questionName + ", questionType=" + questionType
                + ", surveyId=" + surveyId + "]";
    }
}
