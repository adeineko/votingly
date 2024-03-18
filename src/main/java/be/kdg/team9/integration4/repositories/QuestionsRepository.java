package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {
    @Query("SELECT questions FROM Question questions")
    List<Question> findAllQuestions();

    Question findById(long id);

}
