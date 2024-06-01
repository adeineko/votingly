package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.repositories.OptionRepository;
import be.kdg.team9.integration4.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionsRepository questionsRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public QuestionService(QuestionsRepository questionsRepository, OptionRepository optionRepository) {
        this.questionsRepository = questionsRepository;
        this.optionRepository = optionRepository;
    }
    @Transactional
    public List<Question> getAllQuestions() {
        return questionsRepository.findAllQuestions();
    }
    @Transactional
    public Question getQuestion(long id) {
        return questionsRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Question> findAllQuestionById(long id) {
        return questionsRepository.findAllBySurveyIdFetched(id);
    }

    public void addQuestions(List<Question> questions) {
        for (Question question : questions) {
            questionsRepository.insertQuestion(question.getQuestionName(), question.getQuestionType().toString(), question.getSurvey().getSurveyId());
        }
    }

    public List<Question> getQuestionsBySurvey(Survey survey) {
        return questionsRepository.getQuestionsBySurvey(survey);
    }

    @Transactional
    public Question saveQuestion(Question question) {
        return questionsRepository.save(question);
    }

    public void deleteQuestionsBySurvey(Survey survey) {
        List<Question> questions = questionsRepository.getQuestionsBySurvey(survey);
        for (Question question : questions) {
            if (question.getClass().equals(ChoiceQuestion.class)) {
                optionRepository.deleteByQuestion(question);
            }
            questionsRepository.delete(question);
        }
    }
}
