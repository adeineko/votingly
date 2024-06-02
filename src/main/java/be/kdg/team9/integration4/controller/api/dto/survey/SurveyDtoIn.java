package be.kdg.team9.integration4.controller.api.dto.survey;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.model.SurveyType;

import java.util.Date;
import java.util.List;

public class SurveyDtoIn {
    private long surveyId;
    private String surveyName;
    private SurveyType surveyType;
    private List<QuestionDtoIn> questions;

    private Date startDate;
    private Date endDate;

    public SurveyDtoIn() {
    }

    public SurveyDtoIn(String surveyName, SurveyType surveyType, List<QuestionDtoIn> questions, Date startDate, Date endDate) {
        this.surveyName = surveyName;
        this.surveyType = surveyType;
        this.questions = questions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SurveyDtoIn(long surveyId, String surveyName, SurveyType surveyType, List<QuestionDtoIn> questions, Date startDate, Date endDate) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyType = surveyType;
        this.questions = questions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public List<QuestionDtoIn> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDtoIn> questions) {
        this.questions = questions;
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

    @Override
    public String toString() {
        return "SurveyDtoIn [surveyId=" + surveyId + ", surveyName=" + surveyName + ", surveyType=" + surveyType
                + ", questions=" + questions + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
