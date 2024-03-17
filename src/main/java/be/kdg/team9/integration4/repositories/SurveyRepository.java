package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Survey findBySurveyId(long surveyId);

    @Query("""   
            select survey from Survey survey
            left join fetch survey.questions question
            where question.survey.surveyId = :surveyId
             """)
    Optional<Survey> getQuestionOfSurvey(long surveyId);
}
