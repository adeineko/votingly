package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.service.FormService;
import be.kdg.team9.integration4.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/surveys")
public class FormController {

    private final FormService formService;
    private final QuestionService questionService;

    public FormController(FormService formService, QuestionService questionService) {
        this.formService = formService;
        this.questionService = questionService;
    }

    @GetMapping
    public String form(Model model) {
        return "surveys";
    }

    @GetMapping("/{id}/questions")
    public String getSurvey(@PathVariable("id") long id, Model model) {
        model.addAttribute("survey", formService.getForm(id));
        model.addAttribute("question", formService.getQuestionOfForm(id));
        return "survey";
    }
}
