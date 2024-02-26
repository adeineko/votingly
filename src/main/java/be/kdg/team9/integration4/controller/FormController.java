package be.kdg.team9.integration4.controller;

import be.kdg.team9.integration4.service.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("surveys", formService.getAllForms());
        return "index";
    }
}
