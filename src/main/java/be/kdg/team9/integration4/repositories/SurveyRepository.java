package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>, FindAllQuestionBySurveyId {
    Survey findBySurveyId(long surveyId);

    @Query("""   
            select survey from Survey survey
            left join fetch survey.questions question
            where question.survey.surveyId = :surveyId
             """)
    List<Survey> getQuestionOfSurvey(long surveyId);

    @Query("SELECT q.id FROM Question q WHERE q.survey.surveyId = :surveyId")
    List<Long> getQuestionIdsBySurveyId(@Param("surveyId") Long surveyId);
}
