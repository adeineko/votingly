package be.kdg.team9.integration4.controller.api.dto.questions;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.QuestionType;
import be.kdg.team9.integration4.model.question.RangeQuestion;

public class RangeDto extends QuestionDto {

    private int min;
    private int max;
    private int step;

    public RangeDto(long id, String questionName, QuestionType questionType, long surveyId, int min, int max, int step) {
        super(id, questionName, questionType, surveyId);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public RangeDto() {
    }

    public RangeDto(RangeQuestion rangeQuestion) {
        super(rangeQuestion.getId(), rangeQuestion.getQuestionName(), rangeQuestion.getQuestionType(), rangeQuestion.getSurvey().getSurveyId());
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
}
