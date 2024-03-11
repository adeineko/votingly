package be.kdg.team9.integration4.model.question;

import be.kdg.team9.integration4.model.Form;
import be.kdg.team9.integration4.model.QuestionType;
import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "question_name", nullable = false)
    private String questionName;

    @Column(name = "question_type", nullable = false)
    @Enumerated(EnumType.STRING)
    QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    public Question() {
    }

    public Question(long id, String questionName, QuestionType questionType) {
        this.id = id;
        this.questionName = questionName;
        this.questionType = questionType;
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

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
