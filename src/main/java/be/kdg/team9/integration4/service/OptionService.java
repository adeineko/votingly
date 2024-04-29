package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.repositories.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> getOptionsByQuestionId(long questionId) {
        return optionRepository.findAllByQuestionId(questionId);
    }
}
