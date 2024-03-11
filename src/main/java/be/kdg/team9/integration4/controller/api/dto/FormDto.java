package be.kdg.team9.integration4.controller.api.dto;

import be.kdg.team9.integration4.model.FormType;
import jakarta.persistence.*;

import java.time.LocalDate;

public class FormDto {
    private long id;
    private String formName;
    FormType formType;

    public FormDto() {
    }

    public FormDto(long id, String formName, FormType formType) {
        this.id = id;
        this.formName = formName;
        this.formType = formType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }
}
