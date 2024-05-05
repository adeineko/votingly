package be.kdg.team9.integration4.controller.api.dto.answer;

import be.kdg.team9.integration4.model.Option;


import java.util.List;
import java.util.Optional;

public class NewAnswerDto {

    private long surveyId;

    private String answer;

    private int number;

    private List<Option> options;

    public NewAnswerDto() {
    }

    public NewAnswerDto(long surveyId, String answer, int number, List<Option> options) {
        this.answer = answer;
        this.number = number;
        this.options = options;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
