package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class FindAllQuestionBySurveyIdImpl implements FindAllQuestionBySurveyId {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Question> findAllBySurveyIdFetched(Long id) {
        List<ChoiceQuestion> choiceQuestions = entityManager.createQuery(
                        "select cq from ChoiceQuestion cq " +
                                "join fetch cq.options " +
//                "join QuestionSelection qs on cq.id = qs.question.id " +
                                "where cq.survey.id = ?1",
                        ChoiceQuestion.class
                )
                .setParameter(1, id)
                .getResultList();
        List<Question> otherQuestions = entityManager.createQuery(
                        "select q from Question q " +
//                "join QuestionSelection qs on q.id = qs.question.id " +
                                "where q.survey.id = ?1",
                        Question.class
                )
                .setParameter(1, id)
                .getResultList();

        for (ChoiceQuestion choiceQuestion : choiceQuestions)
            if (otherQuestions.contains(choiceQuestion))
                otherQuestions.set(otherQuestions.indexOf(choiceQuestion), choiceQuestion);

        return otherQuestions;
    }
}
