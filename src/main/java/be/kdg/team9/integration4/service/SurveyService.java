package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.repositories.FindAllQuestionBySurveyId;
import be.kdg.team9.integration4.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurvey(long surveyId) {
        return surveyRepository.findBySurveyId(surveyId);
    }

    @Transactional
    public List<Long> getQuestionsOfSurvey(long id) {
        return surveyRepository.getQuestionIdsBySurveyId(id);
    }

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void addSurvey(Survey survey) {
        surveyRepository.save(survey);
    }

    public void delete(long id) {
        surveyRepository.deleteById(id);
    }
}
