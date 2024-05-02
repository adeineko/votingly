package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(OpenAnswer openAnswerEntity);
    Answer save(RangeAnswer rangeAnswerEntity);
}
