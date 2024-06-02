package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.model.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuestionDtoIn {
    private long id;

    @NotBlank
    private String questionName;

    @NotBlank
    private QuestionType questionType;

    @NotNull
    private long surveyId;

    // Range
    private int min;

    private int max;

    private int step;

    // Choice
    private boolean isMultiChoice;

    private List<OptionDto> options;

    public QuestionDtoIn() {
    }

    // Open question
    public QuestionDtoIn(String questionName, QuestionType questionType, long surveyId) {
        this.questionName = questionName;
        this.questionType = questionType;
        this.surveyId = surveyId;
    }

    // Range question
    public QuestionDtoIn(String questionName, QuestionType questionType, long surveyId, int min, int max, int step) {
        this.questionName = questionName;
        this.questionType = questionType;
        this.surveyId = surveyId;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    // Choice question
    public QuestionDtoIn(String questionName, QuestionType questionType, long surveyId, boolean isMultiChoice, List<OptionDto> options) {
        this.questionName = questionName;
        this.questionType = questionType;
        this.surveyId = surveyId;
        this.isMultiChoice = isMultiChoice;
        this.options = options;
    }

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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isMultiChoice() {
        return isMultiChoice;
    }

    public void setMultiChoice(boolean isMultiChoice) {
        this.isMultiChoice = isMultiChoice;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "QuestionDtoIn [id=" + id + ", questionName=" + questionName + ", questionType=" + questionType
                + ", surveyId=" + surveyId + ", min=" + min + ", max=" + max + ", step=" + step + ", isMultiChoice="
                + isMultiChoice + ", options=" + options + "]";
    }
}
