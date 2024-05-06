package be.kdg.team9.integration4.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThankYouPageController {
    @GetMapping("/thank-you-page")
    public String getPage() {
        return "thank-you-page";
    }

    @GetMapping("/features")
    public String getFeaturePage() {
        return "features";
    }
}
