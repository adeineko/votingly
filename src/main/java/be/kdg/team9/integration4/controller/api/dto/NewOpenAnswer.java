package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class NewOpenAnswer {
    @NotBlank
    private String answer;

    public NewOpenAnswer() {
    }

    public NewOpenAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
