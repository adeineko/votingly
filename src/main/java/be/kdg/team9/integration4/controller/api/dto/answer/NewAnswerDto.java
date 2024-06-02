package be.kdg.team9.integration4.controller.api.dto.answer;


import java.time.LocalDateTime;

public class NewAnswerDto {

    private long surveyId;

    private String answer;

    private int range_answer;

    private long[] options_answer;

    private LocalDateTime answerTime;

    public NewAnswerDto() {
    }

    public NewAnswerDto(String answer, int range_answer, long[] options_answer, LocalDateTime answerTime) {
        this.answer = answer;
        this.range_answer = range_answer;
        this.options_answer = options_answer;
        this.answerTime = answerTime;
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

    public long[] getOptions_answer() {
        return options_answer;
    }

    public void setOptions_answer(long[] options_answer) {
        this.options_answer = options_answer;
    }

    public int getRange_answer() {
        return range_answer;
    }

    public void setRange_answer(int range_answer) {
        this.range_answer = range_answer;
    }

    public LocalDateTime getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalDateTime answerTime) {
        this.answerTime = answerTime;
    }
}
