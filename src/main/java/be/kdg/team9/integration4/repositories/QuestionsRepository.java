package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long>, FindAllQuestionBySurveyId {
    @Query("SELECT questions FROM Question questions")
    List<Question> findAllQuestions();

    List<Question> getQuestionsBySurvey(Survey survey);

    @Transactional
    void deleteQuestionsBySurvey(Survey survey);

    @Transactional
    void delete(Question question);

    Optional<Question> findBySurveySurveyId(long id);

    Question findByIdAndSurveySurveyId(long id, long surveyId);

}
