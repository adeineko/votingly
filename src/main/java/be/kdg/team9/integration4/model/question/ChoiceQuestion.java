package be.kdg.team9.integration4.model.question;

import be.kdg.team9.integration4.model.Option;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceQuestion extends Question {

    private boolean isMultiChoice;

    @OneToMany(mappedBy = "question")
    private List<Option> options;

    public ChoiceQuestion() {
    }

    public ChoiceQuestion(boolean isMultiChoice, List<Option> options) {
        this.isMultiChoice = isMultiChoice;
        this.options = options;
    }

    public ChoiceQuestion(String questionName, QuestionType questionType, boolean isMultiChoice, List<Option> options) {
        super(questionName, questionType);
        this.isMultiChoice = isMultiChoice;
        this.options = options;
    }

    public ChoiceQuestion(long id, String questionName, QuestionType questionType, boolean isMultiChoice, List<Option> options) {
        super(id, questionName, questionType);
        this.isMultiChoice = isMultiChoice;
        this.options = options;
    }

    public boolean isMultiChoice() {
        return isMultiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        isMultiChoice = multiChoice;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
