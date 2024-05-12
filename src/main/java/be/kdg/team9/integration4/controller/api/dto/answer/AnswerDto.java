package be.kdg.team9.integration4.controller.api.dto.answer;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.question.Question;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.List;

public class AnswerDto {
    private long answerId;
    private long surveyId;
    private long userId;
    private Question questionId;

    private String answer;

    private List<Option> options_answer;

    private int range_answer;

    private LocalTime answerTime;


    public AnswerDto() {
    }

    public AnswerDto(long answerId,
                     long surveyId,
                     long userId,
                     Question questionId,
                     String answer,
                     List<Option> options_answer,
                     int range_answer,
                     LocalTime answerTime
    ) {
        this.answerId = answerId;
        this.surveyId = surveyId;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.options_answer = options_answer;
        this.range_answer = range_answer;
        this.answerTime = answerTime;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
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

    public LocalTime getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalTime answerTime) {
        this.answerTime = answerTime;
    }
}


