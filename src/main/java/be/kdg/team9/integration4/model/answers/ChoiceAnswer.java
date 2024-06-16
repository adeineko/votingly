package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.annotation.Nullable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceAnswer extends Answer {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    @Nullable
    private Option option;

    public ChoiceAnswer() {
    }

    public ChoiceAnswer(Option option) {
        this.option = option;
    }

    public ChoiceAnswer(long surveyId, long userId, Question question, Option option, LocalDateTime answerTime) {
        super(surveyId, userId, question, answerTime);
        this.option = option;
    }

    public Option getoption() {
        return option;
    }

    public void setoption(Option options) {
        this.option = options;
    }
}
