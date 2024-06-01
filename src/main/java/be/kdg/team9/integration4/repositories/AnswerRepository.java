package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(OpenAnswer openAnswerEntity);

    Answer save(RangeAnswer rangeAnswerEntity);

    @Query("SELECT a FROM Answer a JOIN FETCH a.question WHERE a.surveyId = :surveyId")
    List<Answer> findBySurveyId(long surveyId);
}
