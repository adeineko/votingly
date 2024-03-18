package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.OpenAnswer;
import be.kdg.team9.integration4.model.QuestionType;
import be.kdg.team9.integration4.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {
    private final AnswerService answerService;

    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    private ResponseEntity<Answer> saveAnswerForQuestion(@RequestParam("answerId") answerId,
                                                         @RequestBody Answer answer) {
        Answer savedAnswer = answerService.save(answer);
        return ResponseEntity.ok(savedAnswer);
    }
}
