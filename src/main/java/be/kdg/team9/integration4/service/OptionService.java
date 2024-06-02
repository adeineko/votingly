package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OptionService {
    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository, OptionRepository optionRepository1) {
        this.optionRepository = optionRepository1;
    }

//    public List<Question> getOptionsOfQuestion(long questionId) {
//        return optionRepository.getOptionsOfQuestion(questionId);
//    }
}
