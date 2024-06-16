package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.QuestionType;
import be.kdg.team9.integration4.repositories.OptionRepository;
import be.kdg.team9.integration4.repositories.QuestionsRepository;
import be.kdg.team9.integration4.repositories.SurveyRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {
    private final QuestionsRepository questionsRepository;
    private final OptionRepository optionRepository;
    private final OptionService optionService;
    private final SurveyRepository surveyRepository;

    @Autowired
    public QuestionService(QuestionsRepository questionsRepository, OptionRepository optionRepository, OptionService optionService, SurveyRepository surveyRepository) {
        this.questionsRepository = questionsRepository;
        this.optionRepository = optionRepository;
        this.optionService = optionService;
        this.surveyRepository = surveyRepository;
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

    public void addQuestions(List<Question> questions) {
        for (Question question : questions) {
            if (question.getQuestionType() == QuestionType.CHOICE) {
                ChoiceQuestion choiceQuestion = (ChoiceQuestion) question;
                questionsRepository.save(choiceQuestion);
                optionService.addOptions(choiceQuestion.getOptions());
            } else {
                questionsRepository.save(question);
            }
        }
    }
//
//    @Transactional
//    public Question updateQuestion(long questionId, long surveyId, Question question) {
//        var questionPresent = questionsRepository.findByIdAndSurveySurveyId(questionId, surveyId);
//        if(questionPresent == null) {
//            return null;
//        }
//
//        questionPresent.setQuestionName(question.getQuestionName());
//        questionPresent.setQuestionType(question.getQuestionType());
//        return questionPresent;
//    }

//    @Transactional
//    public Question updateQuestion(long surveyId, Question question) {
//        Question questionPresent = questionsRepository.findBySurveySurveyId(surveyId).orElse(null);
//        if(questionPresent == null) {
//            return null;
//        }
//
//        questionPresent.setQuestionName(question.getQuestionName());
//        questionPresent.setQuestionType(question.getQuestionType());
//        return questionPresent;
//    }

    public Question saveQuestion(Question question) {
        Question savedQuestion = questionsRepository.save(question);
        if (question instanceof ChoiceQuestion choiceQuestion){
            optionService.addOptions(choiceQuestion.getOptions());
        }
        return savedQuestion;
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
