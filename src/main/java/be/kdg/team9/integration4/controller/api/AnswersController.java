package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.answer.NewOpenAnswerDto;
import be.kdg.team9.integration4.controller.api.dto.answer.OpenAnswerDto;
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
    public ResponseEntity<OpenAnswerDto> saveAnswerForQuestion(@RequestBody
                                                           @Valid NewOpenAnswerDto openAnswerDto) {
        var createdAnswer = answerService.save(openAnswerDto.getSurveyId(),
                openAnswerDto.getUserId(),
                openAnswerDto.getQuestionId(),
                openAnswerDto.getAnswer()
        );
        return new ResponseEntity<>(
                modelMapper.map(createdAnswer, OpenAnswerDto.class),
                HttpStatus.CREATED
        );
    }
//    @PostMapping("/{questionId}")
//    public ResponseEntity<OpenAnswerDto> saveAnswerForQuestion(@RequestBody
//                                                           @Valid OpenAnswerDto answer){
//        switch ()
//    }
}