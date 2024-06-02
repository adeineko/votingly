package be.kdg.team9.integration4.converters;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDto;
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

    public SurveyDto convertToDto(Survey survey) {
        return new SurveyDto(survey.getSurveyId(),survey.getSurveyName(),survey.getSurveyType(),survey.getStartDate(),survey.getEndDate());
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
