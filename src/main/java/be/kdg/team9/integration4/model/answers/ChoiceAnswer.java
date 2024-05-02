package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceAnswer extends Answer {

    @OneToMany(mappedBy = "answer")
    private List<Option> options;

    public ChoiceAnswer() {
    }

    public ChoiceAnswer(List<Option> options) {
        this.options = options;
    }

    public ChoiceAnswer(long surveyId, long userId, Question question, List<Option> options) {
        super(surveyId, userId, question);
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
