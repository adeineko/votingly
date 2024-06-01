package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceAnswer extends Answer {

    @OneToMany(mappedBy = "answer")
    private List<Option> options_answer;

    public ChoiceAnswer() {
    }

    public ChoiceAnswer(List<Option> options_answer) {
        this.options_answer = options_answer;
    }

    public ChoiceAnswer(long surveyId, long userId, Question question, List<Option> options_answer, LocalDateTime answerTime) {
        super(surveyId, userId, question, answerTime);
        this.options_answer = options_answer;
    }

    public List<Option> getOptions_answer() {
        return options_answer;
    }

    public void setOptions_answer(List<Option> options) {
        this.options_answer = options;
    }
}
