package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.model.Answer;
import be.kdg.team9.integration4.model.OpenAnswer;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.model.QuestionType;
import be.kdg.team9.integration4.service.AnswerService;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {
    private final AnswerService answerService;

    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/open/{questionId}")
    public ResponseEntity<OpenAnswer> saveAnswerForQuestion(
            @RequestBody OpenAnswer openAnswer,
            @PathVariable Question questionId) {
        if (openAnswer != null) {
            openAnswer.setQuestion(questionId);

            OpenAnswer savedAnswer = answerService.save(
                    openAnswer.getUserId(),
                    openAnswer.getSurveyId(),
                    openAnswer.getQuestion(),
                    openAnswer.getAnswer()
            );
            return ResponseEntity.ok(savedAnswer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
