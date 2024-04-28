package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.RangeDto;
import be.kdg.team9.integration4.converters.QuestionDtoConverter;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.model.RangeQuestion;
import be.kdg.team9.integration4.service.QuestionService;
import be.kdg.team9.integration4.service.SurveyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveysController {
    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final QuestionDtoConverter questionDtoConverter;

    @Autowired
    public SurveysController(SurveyService surveyService, QuestionService questionService, ModelMapper modelMapper, QuestionDtoConverter questionDtoConverter) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
        this.questionDtoConverter = questionDtoConverter;
    }

    @GetMapping
    List<SurveyDto> getAllSurveys() {
        return surveyService.getAllSurveys()
                .stream()
                .map(surveyDto -> modelMapper.map(surveyDto, SurveyDto.class)).toList();
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
}
