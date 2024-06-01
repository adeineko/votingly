package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RANGE")
public class RangeAnswer extends Answer {

    private int range_answer;

    public RangeAnswer() {

    }

    public RangeAnswer(int range_answer) {
        this.range_answer = range_answer;
    }

    public RangeAnswer(long surveyId, long userId, Question question, int range_answer, LocalDateTime answerTime) {
        super(surveyId, userId, question, answerTime);
        this.range_answer = range_answer;
    }

    public int getRange_answer() {
        return range_answer;
    }

    public void setRange_answer(int range_answer) {
        this.range_answer = range_answer;
    }
}
