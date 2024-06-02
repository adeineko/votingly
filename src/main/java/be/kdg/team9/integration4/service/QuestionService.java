package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.OpenQuestion;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.RangeQuestion;
import be.kdg.team9.integration4.repositories.OptionRepository;
import be.kdg.team9.integration4.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {
    private final QuestionsRepository questionsRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public QuestionService(QuestionsRepository questionsRepository, OptionRepository optionRepository) {
        this.questionsRepository = questionsRepository;
        this.optionRepository = optionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionsRepository.findAllQuestions();
    }

    public Question getQuestion(long id) {
        return questionsRepository.findById(id).orElse(null);
    }

    public List<Question> findAllQuestionById(long id) {
        return questionsRepository.findAllBySurveyIdFetched(id);
    }

//    public void addQuestions(List<Question> questions) {
//        for (Question question : questions) {
//            questionsRepository.insertQuestions(question.getQuestionName(), question.getQuestionType().toString(), question.getSurvey().getSurveyId());
//        }
//    }
    public void addQuestions(List<Question> questions) {
        for (Question question : questions) {
            if (question instanceof ChoiceQuestion choiceQuestion) {
                questionsRepository.insertChoiceQuestion(
                        choiceQuestion.getQuestionName(),
                        choiceQuestion.getQuestionType().toString(),
                        choiceQuestion.getSurvey().getSurveyId(),
                        choiceQuestion.isMultiChoice()
                );
            } else if (question instanceof RangeQuestion rangeQuestion) {
                questionsRepository.insertRangeQuestion(
                        rangeQuestion.getQuestionName(),
                        rangeQuestion.getQuestionType().toString(),
                        rangeQuestion.getSurvey().getSurveyId(),
                        rangeQuestion.getMin(),
                        rangeQuestion.getMax(),
                        rangeQuestion.getStep()
                );
            } else if (question instanceof OpenQuestion openQuestion) {
                questionsRepository.insertQuestions(
                        openQuestion.getQuestionName(),
                        openQuestion.getQuestionType().toString(),
                        openQuestion.getSurvey().getSurveyId()
                );
            }
        }
    }


    public List<Question> getQuestionsBySurvey(Survey survey) {
        return questionsRepository.getQuestionsBySurvey(survey);
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
