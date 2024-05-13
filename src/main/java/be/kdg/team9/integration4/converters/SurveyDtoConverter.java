package be.kdg.team9.integration4.converters;

import be.kdg.team9.integration4.controller.api.dto.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.RangeQuestion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyDtoConverter {


    public Survey convertFromDto(SurveyDto surveyDto) {
        return new Survey(surveyDto.getSurveyId(), surveyDto.getSurveyName(), surveyDto.getSurveyType(), surveyDto.getStartDate(), surveyDto.getEndDate());
    }

    private SurveyDto convertToDto(Survey survey) {
//        List<QuestionDto> questionDtos = survey.getQuestions()
////                .stream()
////                .map(question -> new QuestionDto(
////                        question.getId(),
////                        question.getQuestionName(),
////                        question.getQuestionType(),
////                        question.getSurvey()
////                ))
////                .collect(Collectors.toList());
        SurveyDto surveyDto = new SurveyDto(survey);
        surveyDto.setSurveyId(survey.getSurveyId());
        surveyDto.setSurveyName(survey.getSurveyName());
        surveyDto.setSurveyType(survey.getSurveyType());
        surveyDto.setEndDate(survey.getEndDate());
        surveyDto.setStartDate(survey.getStartDate());
//        surveyDto.setQuestions(questionDtos);
        return surveyDto;
    }
//public SurveyDto convert(Survey survey) {
//    List<QuestionDto> questionDtos = survey.getQuestions().stream()
//            .map(question -> modelMapper.map(question, QuestionDto.class))
//            .collect(Collectors.toList());
//    SurveyDto dto = new SurveyDto(
//            survey.getSurveyId(),
//            survey.getSurveyName(),
//            survey.getSurveyType(),
//            questionDtos,
//            survey.getStartDate(),
//            survey.getEndDate());
//    return dto;
//}


}
