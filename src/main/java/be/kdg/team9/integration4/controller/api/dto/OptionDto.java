package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.ChoiceQuestion;
import be.kdg.team9.integration4.model.Option;

public class OptionDto {

    private long optionId;

    private String optionText;

    private ChoiceQuestion question;

    public OptionDto() {
    }

    public OptionDto(long optionId, String optionText, ChoiceQuestion question) {
        this.optionId = optionId;
        this.optionText = optionText;
        this.question = question;
    }

    public OptionDto(Option option) {
    }


    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public ChoiceQuestion getChoice() {
        return question;
    }

    public void setChoice(ChoiceQuestion question) {
        this.question = question;
    }
}
