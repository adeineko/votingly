package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RANGE")
public class RangeAnswer extends Answer {

    private int number;

    public RangeAnswer() {

    }

    public RangeAnswer(int number) {
        this.number = number;
    }

    public RangeAnswer(long surveyId, long userId, Question question, int number) {
        super(surveyId, userId, question);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
