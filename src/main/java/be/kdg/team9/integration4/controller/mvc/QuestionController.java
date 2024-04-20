package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "survey";
    }
}
