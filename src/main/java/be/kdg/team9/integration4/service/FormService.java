package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Form;
import be.kdg.team9.integration4.repositories.FormJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final FormJpaRepo FormJpaRepo;

    public FormService(FormJpaRepo FormJpaRepo) {
        this.FormJpaRepo = FormJpaRepo;
    }

    public List<Form> getAllForms() {
        return FormJpaRepo.findAll();
    }
}