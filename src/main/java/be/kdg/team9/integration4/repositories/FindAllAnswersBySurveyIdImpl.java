package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class FindAllAnswersBySurveyIdImpl implements FindAllAnswersBySurveyId {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Answer> findAllBySurveyIds(Long id) {
        List<ChoiceAnswer> choiceAnswers = entityManager.createQuery(
                        "select ca from ChoiceAnswer ca " +
                                "join fetch ca.option " +
                                "where ca.surveyId = ?1",
                        ChoiceAnswer.class
                )
                .setParameter(1, id)
                .getResultList();
        List<Answer> otherAnswers = entityManager.createQuery(
                        "select an from Answer an " +
                                "where an.surveyId= ?1",
                        Answer.class
                )
                .setParameter(1, id)
                .getResultList();

        for (ChoiceAnswer choiceQuestion : choiceAnswers)
            if (otherAnswers.contains(choiceQuestion))
                otherAnswers.set(otherAnswers.indexOf(choiceQuestion), choiceQuestion);

        return otherAnswers;
    }
}
