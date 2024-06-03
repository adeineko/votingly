package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.model.SurveyType;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
