package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Integer> {
    Form findById(long formId);

    @Query("""   
            select form from Form form
            left join fetch form.questions question
            where question.form.id = :formId
             """)
    Optional<Form> getQuestionOfForm(long formId);
}
