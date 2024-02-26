package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepo extends JpaRepository<Survey, Integer> {
}
