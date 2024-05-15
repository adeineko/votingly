package be.kdg.team9.integration4.repositories;

// import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long>, FindAllQuestionBySurveyId {
    @Query("SELECT questions FROM Question questions")
    List<Question> findAllQuestions();


    List<Question> getQuestionsBySurvey(Survey survey);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO question(question_name, question_type, survey_id, is_multi_choice) VALUES (:questionName, :questionType, :surveyId, 'false')", nativeQuery = true)
    void insertQuestion(String questionName, String questionType, Long surveyId);
}
