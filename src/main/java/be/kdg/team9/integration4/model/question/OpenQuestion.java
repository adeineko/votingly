package be.kdg.team9.integration4.model.question;

import be.kdg.team9.integration4.model.QuestionType;

public class OpenQuestion extends Question {
    public OpenQuestion() {
    }

    public OpenQuestion(long id, String questionName, QuestionType questionType) {
        super(id, questionName, questionType);
    }
}
