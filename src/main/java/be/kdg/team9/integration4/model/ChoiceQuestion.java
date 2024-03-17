package be.kdg.team9.integration4.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceQuestion extends Question {
    private boolean isMultiChoice;
    private List<Option> options;

    public ChoiceQuestion() {
    }
}
