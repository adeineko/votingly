package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Transactional
    void deleteByQuestion(Question question);
}
