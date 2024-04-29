package be.kdg.team9.integration4.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("RANGE")
public class RangeQuestion extends Question {

    private int min;
    private int max;
    private int step;

    public RangeQuestion() {
    }

//    public RangeQuestion(int min, int max, int step) {
//        this.min = min;
//        this.max = max;
//        this.step = step;
//    }

    public RangeQuestion(long id, String questionName, QuestionType questionType, int min, int max, int step) {
        super(id, questionName, questionType);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
