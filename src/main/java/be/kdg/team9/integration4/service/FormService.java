package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Form;
import be.kdg.team9.integration4.repositories.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final FormRepository FormJpaRepo;

    @Autowired
    public FormService(FormRepository FormJpaRepo) {
        this.FormJpaRepo = FormJpaRepo;
    }

    public List<Form> getAllForms() {
        return FormJpaRepo.findAll();
    }

    public Form getForm(long formId) {
        return FormJpaRepo.findById(formId);
    }

    public Form getQuestionOfForm(long formId) {
        return FormJpaRepo.getQuestionOfForm(formId).orElse(null);
    }
}
