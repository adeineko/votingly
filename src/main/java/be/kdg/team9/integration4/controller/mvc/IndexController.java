package be.kdg.team9.integration4.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping
    public String getIndexPage() {
        return "index";
    }

}
