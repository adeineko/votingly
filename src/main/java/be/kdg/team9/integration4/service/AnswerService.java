package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.OpenAnswer;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public OpenAnswer save(long answerId,long surveyId, long questionId, long userId, String answer) {
        var answerEntity = new OpenAnswer(answerId, surveyId, questionId, userId, answer);
        return (OpenAnswer) answerRepository.save(answerEntity);
    }
}
