package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.ChoiceQuestion;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
//    @Query("""
//            select choiceQuestion from ChoiceQuestion choiceQuestion
//            left join fetch choiceQuestion.options options
//             where choiceQuestion.options = :choice
//             """)
//    Optional<Survey> getOptionsOfChoiceQuestion(long choiceQuestionId);

    List<Option> getOptionByQuestionId(long question_id);
}
