package be.kdg.team9.integration4.controller.mvc.viewmodel;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.SurveyType;
import be.kdg.team9.integration4.model.question.Question;

import java.util.List;

public class SurveyViewModel {
    private Long id;
    private String title;
    private SurveyType type;
    private List<Question> questions;  // Assuming QuestionViewModel is already defined

    // Constructors, Getters, and Setters
    public SurveyViewModel() {
    }

    public SurveyViewModel(Long id, String title, SurveyType type, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.questions = questions;
    }

    public static SurveyViewModel fromDomain(final Survey survey) {
        return new SurveyViewModel(
                survey.getSurveyId(),
                survey.getSurveyName(),
                survey.getSurveyType(),
                survey.getQuestions()
        );
    }

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurveyType getType() {
        return type;
    }

    public void setType(SurveyType type){
        this.type = type;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

