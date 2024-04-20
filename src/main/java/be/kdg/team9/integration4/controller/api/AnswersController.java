package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.AnswerDto;
import be.kdg.team9.integration4.controller.api.dto.NewOpenAnswer;
import org.modelmapper.ModelMapper;
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

    @Autowired
    public AnswersController(AnswerService answerService, ModelMapper modelMapper) {
        this.answerService = answerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/open")
    public ResponseEntity<AnswerDto> saveAnswerForQuestion(@RequestBody
                                                           @Valid NewOpenAnswer openAnswerDto) {
        var createdAnswer = answerService.save(openAnswerDto.getSurveyId(),
                openAnswerDto.getUserId(),
                openAnswerDto.getQuestionId(),
                openAnswerDto.getAnswer()
        );
        return new ResponseEntity<>(
                modelMapper.map(createdAnswer, AnswerDto.class),
                HttpStatus.CREATED
        );
    }
}