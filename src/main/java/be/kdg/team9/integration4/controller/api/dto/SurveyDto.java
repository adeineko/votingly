package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.SurveyType;
import be.kdg.team9.integration4.model.question.Question;

import java.util.Date;
import java.util.List;

public class SurveyDto {
    private long surveyId;
    private String surveyName;
    private SurveyType surveyType;
    private List<QuestionDto> questions;
    // private List<Question> questions;

    private Date startDate;
    private Date endDate;

    public SurveyDto() {
    }

    public SurveyDto(Survey survey) {
        this.surveyId = survey.getSurveyId();
        this.surveyName = survey.getSurveyName();
        this.surveyType = survey.getSurveyType();
        // this.questions = survey.getQuestions();
        this.startDate = survey.getStartDate();
        this.endDate = survey.getEndDate();
    }

    public SurveyDto(long surveyId, String surveyName, SurveyType surveyType, Date startDate, Date endDate) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyType = surveyType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SurveyDto(long surveyId, String surveyName, SurveyType surveyType, List<QuestionDto> questions,
            Date startDate, Date endDate) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyType = surveyType;
        this.questions = questions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // public SurveyDto(long surveyId, String surveyName, SurveyType surveyType, List<Question> questions,
    //         Date startDate, Date endDate) {
    //     this.surveyId = surveyId;
    //     this.surveyName = surveyName;
    //     this.surveyType = surveyType;
    //     this.questions = questions;
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    // }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public SurveyType getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyType surveyType) {
        this.surveyType = surveyType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    // public List<Question> getQuestions() {
    //     return questions;
    // }

    // public void setQuestions(List<Question> questions) {
    //     this.questions = questions;
    // }

}
