package be.kdg.team9.integration4.service;

// import be.kdg.team9.integration4.controller.api.dto.AnswerDto;
// import be.kdg.team9.integration4.controller.api.dto.NewOpenAnswer;
import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.OpenAnswer;
import be.kdg.team9.integration4.model.Question;
// import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.repositories.AnswerRepository;
// import jakarta.persistence.PersistenceContext;
// import jakarta.persistence.PersistenceContextType;
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

    @Transactional
    public Answer save(long surveyId, long userId, Question question, String answer) {
        var answerEntity = new OpenAnswer(surveyId, userId, question, answer);

        return answerRepository.save(answerEntity);
    }
}
