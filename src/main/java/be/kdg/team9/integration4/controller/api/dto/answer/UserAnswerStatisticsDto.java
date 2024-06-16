package be.kdg.team9.integration4.controller.api.dto.answer;

import java.time.LocalDate;

public class UserAnswerStatisticsDto {
    private LocalDate answerTime;
    private long answerCount;

    public UserAnswerStatisticsDto(LocalDate answerTime, long answerCount) {
        this.answerTime = answerTime;
        this.answerCount = answerCount;
    }

    public LocalDate getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalDate answerTime) {
        this.answerTime = answerTime;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }
}
