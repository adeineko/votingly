package be.kdg.team9.integration4.model;

import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import jakarta.persistence.*;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long optionId;

    @Column(name = "option_text")
    private String optionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question", nullable = false)
    private ChoiceQuestion question;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChoiceAnswer answer;

    public Option() {
    }

    public Option(long optionId, String optionText, ChoiceQuestion question) {
        this.optionId = optionId;
        this.optionText = optionText;
        this.question = question;
    }


    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public ChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ChoiceQuestion question) {
        this.question = question;
    }
}
