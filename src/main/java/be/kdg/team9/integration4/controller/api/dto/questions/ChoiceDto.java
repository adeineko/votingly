package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.controller.api.dto.OptionDto;
import be.kdg.team9.integration4.model.ChoiceQuestion;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

public class ChoiceDto extends QuestionDto {

    private boolean isMultiChoice;
    private List<OptionDto> options;

    public ChoiceDto() {
    }
    public ChoiceDto(ChoiceQuestion question) {
        super(question.getId(), question.getQuestionName(), question.getQuestionType(), question.getSurvey().getSurveyId());
        this.isMultiChoice = question.isMultiChoice();
        this.options = question.getOptions().stream()
                .map(option -> new OptionDto(option.getOptionId(), option.getOptionText(), option.getQuestion()))
                .collect(Collectors.toList());
    }

    public ChoiceDto(long id, String questionName, QuestionType questionType, long surveyId, boolean isMultiChoice, List<OptionDto> options) {
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
