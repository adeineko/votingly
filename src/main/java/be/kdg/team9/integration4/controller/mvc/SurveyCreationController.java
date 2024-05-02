package be.kdg.team9.integration4.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SurveyCreationController {
    @GetMapping("/create-survey")
    public String getPage() {
        return "addsurvey";
    }
}
