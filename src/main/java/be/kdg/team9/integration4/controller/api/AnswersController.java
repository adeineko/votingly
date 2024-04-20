package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.AnswerDto;
import be.kdg.team9.integration4.controller.api.dto.NewOpenAnswer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import be.kdg.team9.integration4.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {
        private final AnswerService answerService;
        private final ModelMapper modelMapper;
        private final Logger logger;

        @Autowired
        public AnswersController(AnswerService answerService, ModelMapper modelMapper, Logger logger) {
                this.answerService = answerService;
                this.modelMapper = modelMapper;
                this.logger = logger;
        }

    @PostMapping("/open/{questionId}")
    public ResponseEntity<AnswerDto> saveAnswerForQuestion(
            @RequestBody @Valid NewOpenAnswer newOpenAnswer,
            @PathVariable long questionId) {
        logger.info("Received POST request to save answer for question with id: " + questionId);
        logger.info("Answer: " + newOpenAnswer.getAnswer());
        logger.info("SurveyId: " + newOpenAnswer.getSurveyId());
        logger.info("UserId: " + newOpenAnswer.getUserId());
//        var newAnswer = answerService.save(
//                newOpenAnswer.getAnswer()
//        );
        AnswerDto newAnswer = answerService.save(newOpenAnswer);

        return new ResponseEntity<>(
                modelMapper.map(newAnswer, AnswerDto.class),
                HttpStatus.CREATED
        );

    }
}
// openAnswer.setQuestion(questionId);
//
// OpenAnswer savedAnswer = answerService.save(
// openAnswer.getUserId(),
// openAnswer.getSurveyId(),
// openAnswer.getQuestion(),
// openAnswer.getAnswer()
// );
// return ResponseEntity.ok(savedAnswer);
// } else {
// return ResponseEntity.badRequest().build();
// }
