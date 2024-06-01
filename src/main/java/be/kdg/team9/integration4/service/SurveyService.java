package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.repositories.FindAllQuestionBySurveyId;
import be.kdg.team9.integration4.repositories.SurveyRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    public ByteArrayInputStream exportSurveyToCSV(long surveyId) {
        Survey survey = surveyRepository.findBySurveyId(surveyId);
        if (survey == null) {
            return null;
        }

        List<Survey> surveys = new ArrayList<>();
        surveys.add(survey);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader("ID", "Name", "Type", "Start Date", "End Date"))) {
            for (Survey s : surveys) {
                csvPrinter.printRecord(s.getSurveyId(), s.getSurveyName(), s.getSurveyType(), s.getStartDate(), s.getEndDate());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace(); // Improve error handling
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
