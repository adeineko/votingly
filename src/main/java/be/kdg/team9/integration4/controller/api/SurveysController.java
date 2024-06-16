package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.controller.api.dto.questions.UpdateQuestionDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDtoIn;
import be.kdg.team9.integration4.controller.api.dto.survey.UpdateSurveyDto;
import be.kdg.team9.integration4.converters.QuestionDtoConverter;
import be.kdg.team9.integration4.converters.SurveyDtoConverter;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.user.PlatformAdmin;
import be.kdg.team9.integration4.model.user.User;
import be.kdg.team9.integration4.security.CustomUserDetails;
import be.kdg.team9.integration4.service.QuestionService;
import be.kdg.team9.integration4.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
public class SurveysController {
    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final QuestionDtoConverter questionDtoConverter;
    private final SurveyDtoConverter surveyDtoConverter;
    private final Logger logger;

    @Autowired
    public SurveysController(SurveyService surveyService, QuestionService questionService, ModelMapper modelMapper, QuestionDtoConverter questionDtoConverter, SurveyDtoConverter surveyDtoConverter, Logger logger) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
        this.questionDtoConverter = questionDtoConverter;
        this.surveyDtoConverter = surveyDtoConverter;
        this.logger = logger;
    }

    @GetMapping
    List<SurveyDto> getAllSurveys() {
        logger.info("Getting all surveys");
        return surveyService.getAllSurveys()
                .stream()
                .map(surveyDtoConverter::convertToDto).toList();
    }


    @GetMapping("/{id}/questions")
    ResponseEntity<List<QuestionDto>> getQuestionsOfSurvey(@PathVariable("id") long surveyId) {
        List<Question> questions = questionService.findAllQuestionById(surveyId);
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<QuestionDto> questionDtos = questions.stream()
                .map(questionDtoConverter::convert)
                .toList();

        return ResponseEntity.ok(questionDtos);
    }

    @PostMapping("/{id}/questions")
    ResponseEntity<QuestionDto> changeQuestionsInForm(@PathVariable long id, @RequestBody @Valid UpdateQuestionDto questionDtoIn) {
        var survey = surveyService.getSurvey(id);
        Question question = questionDtoConverter.convertFromDtoIn(questionDtoIn, survey);
        Question savedQuestions = questionService.saveQuestion(question);
        return ResponseEntity.ok(questionDtoConverter.convert(savedQuestions));
    }

    @PostMapping
    public ResponseEntity<SurveyDtoIn> addSurvey(@RequestBody SurveyDtoIn surveyDto) {
        logger.info(surveyDto.toString());
        Survey survey = modelMapper.map(surveyDto, Survey.class);
        List<Question> questions = surveyDto.getQuestions().stream()
                .map(questionDtoIn -> questionDtoConverter.convertFromDtoIn(questionDtoIn, survey)).toList();

        surveyService.addSurvey(survey);
        questionService.addQuestions(questions);

        return ResponseEntity.status(HttpStatus.CREATED).body(surveyDto);
    }

//    @PatchMapping("{id}")
//    ResponseEntity<SurveyDto> changeSurvey(@PathVariable("id") long surveyId,
//                                           @RequestBody @Valid SurveyDto updatedSurveyDto) {
//        Survey survey = surveyDtoConverter.convertFromDto(updatedSurveyDto);
//
//        Survey updatedSurvey = surveyService.changeSurveyInfo(surveyId, survey);
//        return ResponseEntity.ok(surveyDtoConverter.convertToDto(updatedSurvey));
//    }

    @PatchMapping("{id}")
    ResponseEntity<Void> updateSurvey(@PathVariable("id") long surveyId,
                                      @RequestBody @Valid UpdateSurveyDto updateSurveyDto) {
        List<Question> updatedQuestions = updateSurveyDto.getQuestions().stream()
                .map(questionDto -> modelMapper.map(questionDto, Question.class))
                .collect(Collectors.toList());

        if (surveyService.changeSurveyInfo(surveyId, updateSurveyDto.getSurveyName(), updateSurveyDto.getSurveyType(), updatedQuestions)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<SurveyDto> getSurveyDetails(@PathVariable("id") long id,
                                                      @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Survey survey = surveyService.getSurvey(id);
        List<Question> questions = questionService.findAllQuestionById(id);
        List<QuestionDto> questionDtos = questions.stream()
                .map(questionDtoConverter::convert)
                .toList();

        SurveyDto surveyDto = new SurveyDto(
                survey.getSurveyId(),
                survey.getSurveyName(),
                survey.getSurveyType(),
                questionDtos
        );
        return ResponseEntity.ok(surveyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(
            @PathVariable("id") final long id
    ) {
        Survey survey = surveyService.getSurvey(id);
        questionService.deleteQuestionsBySurvey(survey);
        surveyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/supervisor")
    public String saveNote(@RequestBody String note, @PathVariable("id") long id) {
        logger.info(note);
        surveyService.addNoteToSurvey(id, note);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).toString();
    }

}
