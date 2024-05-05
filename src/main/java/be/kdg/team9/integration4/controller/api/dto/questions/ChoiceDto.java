package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.QuestionType;

import java.util.List;

public class ChoiceDto extends QuestionDto {

    private boolean isMultiChoice;
    private List<OptionDto> options;

    public ChoiceDto() {
    }

    public ChoiceDto(ChoiceQuestion question) {
        super(question.getId(), question.getQuestionName(), question.getQuestionType(), question.getSurvey());
    }

    public ChoiceDto(long id, String questionName, QuestionType questionType, Survey surveyId, boolean isMultiChoice, List<OptionDto> options) {
        super(id, questionName, questionType, surveyId);
        this.isMultiChoice = isMultiChoice;
        this.options = options;
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
