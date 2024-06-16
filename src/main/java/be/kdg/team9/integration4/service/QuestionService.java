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

    public void updateQuestions(long surveyId, List<Question> updatedQuestions, String name) {
        var survey = surveyRepository.findById(surveyId).orElse(null);
        if (survey == null) {
            System.out.print("Survey not found with id " + surveyId);
            return;
        }

        // Fetch the existing questions for the survey
        List<Question> existingQuestions = questionsRepository.findAllBySurveyIdFetched(surveyId);

        // Map the updated questions by their IDs (using Long instead of long)
        Map<Long, Question> updatedQuestionsMap = updatedQuestions.stream()
//                .filter(q -> q.getId() != null)
                .collect(Collectors.toMap(Question::getId, q -> q, (existing, replacement) -> replacement));

        // Update or delete existing questions
//        for (Question existingQuestion : existingQuestions) {
//            if (updatedQuestionsMap.containsKey(existingQuestion.getId())) {
//                Question updatedQuestion = updatedQuestionsMap.get(existingQuestion.getId());
//                existingQuestion.updateFrom(updatedQuestion);
//                questionsRepository.save(existingQuestion);
//                updatedQuestionsMap.remove(existingQuestion.getId());
//            } else {
//                questionsRepository.delete(existingQuestion);
//            }
//        }

        // Add new questions
        for (Question question : updatedQuestionsMap.values()) {
            question.setForm(survey);
            if (question.getQuestionType() == QuestionType.CHOICE) {
                ChoiceQuestion choiceQuestion = (ChoiceQuestion) question;
                choiceQuestion.setQuestionName(name);
                choiceQuestion.setQuestionType(choiceQuestion.getQuestionType());
                choiceQuestion.setMultiChoice(choiceQuestion.isMultiChoice());
                // Save the choice question first to get its ID
                questionsRepository.save(choiceQuestion);

                // Now save the options with the reference to the saved choice question
                for (Option option : choiceQuestion.getOptions()) {
                    option.setQuestion(choiceQuestion);
                }
                optionService.addOptions(choiceQuestion.getOptions());
            } else {
                questionsRepository.save(question);
            }
        }
    }

    public void addQuestion(Question question) {
        questionsRepository.save(question);
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
