package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.QuestionDto;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.service.SurveyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveys")
public class SurveysController {
    private SurveyService surveyService;
    private ModelMapper modelMapper;

    @Autowired
    public SurveysController(SurveyService surveyService, ModelMapper modelMapper) {
        this.surveyService = surveyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<SurveyDto> getAllSurveys() {
        return surveyService.getAllSurveys()
                .stream()
                .map(surveyDto -> modelMapper.map(surveyDto, SurveyDto.class)).toList();
    }


    @GetMapping("/{id}/questions")
    ResponseEntity<List<QuestionDto>> getQuestionsOfForm(@PathVariable("id") long surveyId) {
        Optional<Survey> optionalSurvey = Optional.ofNullable(surveyService.getQuestionOfSurvey(surveyId));
        if (optionalSurvey.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Survey survey = optionalSurvey.get();

        List<QuestionDto> questionDtos = survey.getQuestions()
                .stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .toList();

        return ResponseEntity.ok(questionDtos);
    }
}
