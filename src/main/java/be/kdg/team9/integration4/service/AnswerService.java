package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import be.kdg.team9.integration4.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer saveOpen(long surveyId, long userId, Question question, String answer) {
        var openAnswerEntity = new OpenAnswer(surveyId, userId, question, answer);

        return answerRepository.save(openAnswerEntity);
    }

    public Answer saveRange(long surveyId, long userId, Question question, String answer) {
        var rangeAnswerEntity = new RangeAnswer(surveyId, userId, question, answer);

        return answerRepository.save(rangeAnswerEntity);
    }

    public Answer saveChoice(long surveyId, long userId, Question question, List<Option> options) {
        var choiceAnswerEntity = new ChoiceAnswer(surveyId, userId, question, options);

        return answerRepository.save(choiceAnswerEntity);
    }

}
