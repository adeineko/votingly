package be.kdg.team9.integration4.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupervisorController {
    @GetMapping("/addSupervisor")
    private String addSupervisorPage(){
        return "addSupervisor";
    }
}
