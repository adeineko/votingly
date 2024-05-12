package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

import java.time.LocalTime;

@Entity
@DiscriminatorValue("OPEN")
public class OpenAnswer extends Answer {
    private String answer;

    public OpenAnswer() {
    }

    public OpenAnswer(String answer) {
        this.answer = answer;
    }


    public OpenAnswer(long surveyId, long userId, Question question, String answer, LocalTime answerTime) {
        super(surveyId, userId, question, answerTime);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
