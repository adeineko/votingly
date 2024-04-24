package be.kdg.team9.integration4.model;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long optionId;

    @Column(name = "option_text")
    private String optionText;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "range_question_id")
//    private RangeQuestion option;

    public Option() {
    }

    public Option(long optionId, String optionText) {
        this.optionId = optionId;
        this.optionText = optionText;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

//    public RangeQuestion getRangeOption() {
//        return option;
//    }
//
//    public void setRangeOption(RangeQuestion rangeOption) {
//        this.option = option;
//    }
}
