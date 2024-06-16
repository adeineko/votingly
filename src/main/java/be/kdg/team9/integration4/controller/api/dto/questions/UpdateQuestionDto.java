package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.model.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateQuestionDto {
    @NotBlank
    private String questionName;

    @NotNull
    private QuestionType questionType;

    // Range
    private int min;

    private int max;

    private int step;

    // Choice
    private boolean isMultiChoice;

    private List<OptionDto> options;

    public UpdateQuestionDto() {
    }

    public UpdateQuestionDto(String questionName, QuestionType questionType,int min, int max, int step, boolean isMultiChoice, List<OptionDto> options) {
        this.questionName = questionName;
        this.questionType = questionType;
        this.min = min;
        this.max = max;
        this.step = step;
        this.isMultiChoice = isMultiChoice;
        this.options = options;
    }

    public @NotBlank String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(@NotBlank String questionName) {
        this.questionName = questionName;
    }

    public @NotNull QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(@NotNull QuestionType questionType) {
        this.questionType = questionType;
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

    public void setMultiChoice(boolean multiChoice) {
        isMultiChoice = multiChoice;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}
