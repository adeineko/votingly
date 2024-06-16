package be.kdg.team9.integration4.controller.api.dto.survey;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.model.SurveyType;

import java.util.List;

public class UpdatedSurveyDto {
    private String surveyName;
    private SurveyType surveyType;
    private List<QuestionDtoIn> questions;

    public UpdatedSurveyDto(String surveyName, SurveyType surveyType, List<QuestionDtoIn> questions) {
        this.surveyName = surveyName;
        this.surveyType = surveyType;
        this.questions = questions;
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
}
