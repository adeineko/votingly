package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.questions.ChoiceDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.controller.api.dto.questions.RangeDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDtoIn;
import be.kdg.team9.integration4.converters.QuestionDtoConverter;
import be.kdg.team9.integration4.converters.SurveyDtoConverter;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.QuestionType;
import be.kdg.team9.integration4.model.question.RangeQuestion;
import be.kdg.team9.integration4.service.OptionService;
import be.kdg.team9.integration4.service.QuestionService;
import be.kdg.team9.integration4.service.SurveyService;

// import org.hibernate.mapping.Map;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final OptionService optionService;

    @Autowired
    public SurveysController(SurveyService surveyService, QuestionService questionService, ModelMapper modelMapper, QuestionDtoConverter questionDtoConverter, SurveyDtoConverter surveyDtoConverter, Logger logger, OptionService optionService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
        this.questionDtoConverter = questionDtoConverter;
        this.surveyDtoConverter = surveyDtoConverter;
        this.logger = logger;
        this.optionService = optionService;
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

//    @PostMapping
//    public ResponseEntity<SurveyDto> addSurvey(
//            @RequestBody SurveyDto surveyDto
//    ) {
//        Survey survey = modelMapper.map(surveyDto, Survey.class);
//        List<Question> questions = new ArrayList<>();
//        for (QuestionDto questionDto : surveyDto.getQuestions()) {
//            Question question = new Question(
//                    questionDto.getQuestionName(),
//                    questionDto.getQuestionType()
//            );
//
//            // Infer isMultiChoice based on questionType
//            boolean isMultiChoice = question.getQuestionType() == QuestionType.CHOICE;
//
//            questions.add(question);
//        }
//        surveyService.addSurvey(survey);
//        questions.forEach(question -> question.setSurvey(survey));
//        questionService.addQuestions(questions);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(surveyDto);
//    }

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


    @GetMapping("/{id}/details")
    public ResponseEntity<SurveyDto> getSurveyDetails(@PathVariable("id") long id) {
        Survey survey = surveyService.getSurvey(id);
        List<Question> questions = questionService.getQuestionsBySurvey(survey);
        List<QuestionDto> questionDtos = questions.stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .toList();
//        survey.setQuestions(questions);
        SurveyDto surveyDto = new SurveyDto(
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


}
