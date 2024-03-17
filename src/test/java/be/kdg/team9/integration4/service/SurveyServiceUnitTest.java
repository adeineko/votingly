package be.kdg.team9.integration4.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import be.kdg.team9.integration4.model.survey.Survey;

// @RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyServiceUnitTest {
    @Autowired
    private SurveyService surveyService;
    
    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<Survey> surveys = surveyService.getAllForms();
    
        assertEquals(surveys.size(), 3);
    }
    
}
