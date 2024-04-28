package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.QuestionDto;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.service.QuestionService;
import be.kdg.team9.integration4.service.SurveyService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    public SurveysController(SurveyService surveyService, QuestionService questionService, ModelMapper modelMapper) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<SurveyDto> getAllSurveys() {
        return surveyService.getAllSurveys()
                .stream()
                .map(surveyDto -> modelMapper.map(surveyDto, SurveyDto.class)).toList();
    }


    @GetMapping("/{id}/questions")
//    ResponseEntity<List<QuestionDto>> getQuestionsOfForm(@PathVariable("id") long surveyId) {
//        Optional<Survey> optionalSurvey = Optional.ofNullable(surveyService.getQuestionOfSurvey(surveyId));
//        if (optionalSurvey.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        Survey survey = optionalSurvey.get();
//
//        List<QuestionDto> questionDtos = survey.getQuestions()
//                .stream()
//                .map(question -> modelMapper.map(question, QuestionDto.class))
//                .toList();
//
//        return ResponseEntity.ok(questionDtos);
//    }
    ResponseEntity<List<QuestionDto>> getQuestionsOfSurvey(@PathVariable("id") long surveyId) {
        List<Question> questions = questionService.findAllQuestionById(surveyId);
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<QuestionDto> questionDtos = questions
                .stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .toList();

        return ResponseEntity.ok(questionDtos);

    }
}
