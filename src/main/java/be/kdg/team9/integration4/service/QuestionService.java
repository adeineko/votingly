package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public List<Question> getAllQuestions() {
        return questionsRepository.findAllQuestions();
    }

    public Question getQuestion(long id) {
        return questionsRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Question> findAllQuestionById(long id) {
        return questionsRepository.findAllBySurveyIdFetched(id);
    }
}
