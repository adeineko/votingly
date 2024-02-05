package com.example.integration4.controller;

import com.example.integration4.service.SurveyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class HomeController {

    private final SurveyService surveyService;

    public HomeController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("surveys", surveyService.getAllSurveys());
        return "index";
    }
}
