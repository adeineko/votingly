package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findAllAnswersBySurveyId(long id) {
        return answerRepository.findAllBySurveyIds(id);
    }

    public Answer saveOpen(long surveyId, long userId, Question question, String answer, LocalDateTime answerTime) {
        var openAnswerEntity = new OpenAnswer(surveyId, userId, question, answer, answerTime);

        return answerRepository.save(openAnswerEntity);
    }

    public Answer saveRange(long surveyId, long userId, Question question, int answer, LocalDateTime answerTime) {
        var rangeAnswerEntity = new RangeAnswer(surveyId, userId, question, answer, answerTime);

        return answerRepository.save(rangeAnswerEntity);
    }

    public Answer saveChoice(long surveyId, long userId, Question question, long optionId, LocalDateTime answerTime) {
        var option = new Option(optionId, "option");
        var choiceAnswerEntity = new ChoiceAnswer(surveyId, userId, question, option, answerTime);

        return answerRepository.save(choiceAnswerEntity);
    }

    public List<Answer> getAllSurveys(long surveyId) {
        return answerRepository.findBySurveyId(surveyId);
    }

}
