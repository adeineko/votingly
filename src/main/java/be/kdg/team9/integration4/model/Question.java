package be.kdg.team9.integration4.model;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.QuestionType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "question_name", nullable = false)
    private String questionName;

    @Column(name = "question_type", nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<Answer> answers;

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

    public Survey getForm() {
        return survey;
    }

    public void setForm(Survey survey) {
        this.survey = survey;
    }
}
