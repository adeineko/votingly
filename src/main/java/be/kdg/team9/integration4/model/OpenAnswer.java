package be.kdg.team9.integration4.model;

import jakarta.persistence.Entity;

@Entity
public class OpenAnswer extends Answer {
    private String answer;

    public OpenAnswer() {
    }

    public OpenAnswer(long answerId, Question question, String answer) {
        super(answerId, question);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
