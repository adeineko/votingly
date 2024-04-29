package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.service.SurveyService;
import be.kdg.team9.integration4.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public String survey() {
        return "surveys";
    }

    @GetMapping("/{id}/questions")
    public String getSurvey(@PathVariable("id") long id, Model model) {
        Survey survey = surveyService.getSurvey(id);
        List<Long> questionIds = surveyService.getQuestionsOfSurvey(id);

        // Add the survey and question IDs to the model
        model.addAttribute("survey", survey);
        model.addAttribute("questionIds", questionIds);
        return "survey";
    }
}
