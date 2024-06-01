package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.answer.*;
//import be.kdg.team9.integration4.converters.ChoiceAnswerDtoConverter;
import be.kdg.team9.integration4.converters.ChoiceAnswerDtoConverter;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.security.CustomUserDetails;
import be.kdg.team9.integration4.service.QuestionService;
import org.modelmapper.ModelMapper;
import be.kdg.team9.integration4.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final ChoiceAnswerDtoConverter choiceAnswerDtoConverter;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswersController(AnswerService answerService, QuestionService questionService, ChoiceAnswerDtoConverter choiceAnswerDtoConverter, ModelMapper modelMapper) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.choiceAnswerDtoConverter = choiceAnswerDtoConverter;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<AnswerDto> saveAnswerForQuestion(@RequestBody
                                                           @Valid NewAnswerDto newAnswerDto,
                                                           @PathVariable Question questionId,
                                                           @AuthenticationPrincipal CustomUserDetails user) {
        var question = questionService.getQuestion(questionId.getId());
        long userId = (user != null) ? user.getUserId() : 0;

        return switch (question.getQuestionType()) {
            case OPEN -> {
                var createdOpenAnswer = answerService.saveOpen(newAnswerDto.getSurveyId(),
                        userId,
                        questionId,
                        newAnswerDto.getAnswer(),
                        LocalDateTime.now()
                );
                yield new ResponseEntity<>(
                        modelMapper.map(createdOpenAnswer, AnswerDto.class),
                        HttpStatus.CREATED
                );
            }
            case RANGE -> {
                var createdRangeAnswer = answerService.saveRange(question.getSurvey().getSurveyId(),
                        userId,
                        questionId,
                        newAnswerDto.getRange_answer(),
                        LocalDateTime.now());
                yield new ResponseEntity<>(
                        modelMapper.map(createdRangeAnswer, AnswerDto.class),
                        HttpStatus.CREATED
                );
            }
            case CHOICE -> {
                var createdChoiceAnswer = answerService.saveChoice(
                        question.getSurvey().getSurveyId(),
                        userId,
                        questionId,
                        newAnswerDto.getOptions_answer(),
                        LocalDateTime.now()
                );
                yield new ResponseEntity<>(
                        modelMapper.map(createdChoiceAnswer, AnswerDto.class),
                        HttpStatus.CREATED
                );
            }
            default ->
                    throw new UnsupportedOperationException("Unsupported question type: " + question.getQuestionType());
        };
    }
}