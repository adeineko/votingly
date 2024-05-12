package be.kdg.team9.integration4.controller.api.dto.answer;

import be.kdg.team9.integration4.model.Option;


import java.time.LocalTime;
import java.util.List;

public class NewAnswerDto {

    private long surveyId;

    private String answer;

    private int range_answer;

    private List<Option> options_answer;

//    private LocalTime answerTime;

    public NewAnswerDto() {
    }

    public NewAnswerDto(long surveyId, String answer, int range_answer, List<Option> options_answer) {
        this.answer = answer;
        this.range_answer = range_answer;
        this.options_answer = options_answer;
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

    public List<Option> getOptions_answer() {
        return options_answer;
    }

    public void setOptions_answer(List<Option> options_answer) {
        this.options_answer = options_answer;
    }

    public int getRange_answer() {
        return range_answer;
    }

    public void setRange_answer(int range_answer) {
        this.range_answer = range_answer;
    }
}
