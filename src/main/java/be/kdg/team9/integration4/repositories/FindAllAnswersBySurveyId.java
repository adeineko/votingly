package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.answers.Answer;

import java.util.List;

public interface FindAllAnswersBySurveyId {
    List<Answer> findAllBySurveyIds(Long surveyId);
}
