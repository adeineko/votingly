package be.kdg.team9.integration4.model.question;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPEN")
public class OpenQuestion extends Question {

    public OpenQuestion() {
    }

    public OpenQuestion(long id, String questionName, QuestionType questionType) {
        super(id, questionName, questionType);
    }
}
