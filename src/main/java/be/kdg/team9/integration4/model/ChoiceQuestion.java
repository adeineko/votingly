package be.kdg.team9.integration4.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceQuestion extends Question {
    private boolean isMultiChoice;

    @OneToMany
    private List<Option> options;

    public ChoiceQuestion() {
    }
}