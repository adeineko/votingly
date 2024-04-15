package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.controller.api.dto.AnswerDto;
import be.kdg.team9.integration4.controller.api.dto.NewOpenAnswer;
import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.OpenAnswer;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.repositories.AnswerRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public AnswerService(AnswerRepository answerRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
    }

    public AnswerDto save(NewOpenAnswer newOpenAnswer) {
//        var answerEntity = new NewOpenAnswer();
//        return answerRepository.save(answerEntity);
        OpenAnswer openAnswer = modelMapper.map(newOpenAnswer, OpenAnswer.class);
        // Here you can set other properties of openAnswer, like questionId
//        openAnswer.setQuestion(questionId);

        OpenAnswer savedAnswerEntity = answerRepository.save(openAnswer);
        return modelMapper.map(savedAnswerEntity, AnswerDto.class);
    }
}
