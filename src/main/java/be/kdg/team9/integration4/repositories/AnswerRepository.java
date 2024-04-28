package be.kdg.team9.integration4.repositories;

// import be.kdg.team9.integration4.controller.api.dto.NewOpenAnswer;
import be.kdg.team9.integration4.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
