package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.service.SurveyService;
import be.kdg.team9.integration4.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("survey", surveyService.getSurvey(id));
        model.addAttribute("question", surveyService.getQuestionOfSurvey(id));
        return "survey";
    }
}
