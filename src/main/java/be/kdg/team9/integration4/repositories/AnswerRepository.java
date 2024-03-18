package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(Answer answer);
}
