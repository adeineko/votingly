package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormJpaRepo extends JpaRepository<Form, Integer> {
}
