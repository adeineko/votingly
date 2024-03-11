package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class QuestionDto {
    private long id;

    private String questionName;

    QuestionType questionType;

    long formId;

    public QuestionDto() {
    }

    public QuestionDto(long id, String questionName, QuestionType questionType, long formId) {
        this.id = id;
        this.questionName = questionName;
        this.questionType = questionType;
        this.formId = formId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }
}
