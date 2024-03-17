package be.kdg.team9.integration4.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long surveyId;

    @Column(name = "survey_name", nullable = false)
    private String surveyName;

    @Column(name = "survey_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SurveyType surveyType;

    @OneToMany(mappedBy = "survey")
    private List<Question> questions;

//    private Date startDate;
//    private Date endDate;
//    private boolean isPublished;
//    private boolean isDefault;
//    private boolean isAllowedOnlyOnce;
//    private long userId;
//    private int timeSpent;
//    private Date submitDate;


    // Constructors, getters, and setters
    public Survey() {
    }

    public Survey(long surveyId, String surveyName, SurveyType surveyType) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyType = surveyType;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long id) {
        this.surveyId = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public SurveyType getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyType surveyType) {
        this.surveyType = surveyType;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
