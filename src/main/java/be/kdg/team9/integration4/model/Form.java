package be.kdg.team9.integration4.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "form")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "form_name", nullable = false)
    private String formName;

    @Column(name = "form_type", nullable = false)
    @Enumerated(EnumType.STRING)
    FormType formType;

    @OneToMany(mappedBy = "form")
    private List<Question> questions;

    // Constructors, getters, and setters
    public Form() {
    }

    public Form(long id, String formName, FormType formType) {
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
