package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RANGE")
public class RangeAnswer extends Answer {

    private String answer;

    public RangeAnswer() {

    }

    public RangeAnswer(String answer) {
        this.answer = answer;
    }

    public RangeAnswer(long surveyId, long userId, Question question, String answer) {
        super(surveyId, userId, question);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
