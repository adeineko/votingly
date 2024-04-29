package be.kdg.team9.integration4.converters;

import be.kdg.team9.integration4.controller.api.dto.OptionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.ChoiceDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.RangeDto;
import be.kdg.team9.integration4.model.ChoiceQuestion;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.model.RangeQuestion;

import java.util.stream.Collectors;

public class QuestionDtoConverter {

    public QuestionDto convert(Question question) {

        QuestionDto dto;
        dto = new QuestionDto(question.getId(), question.getQuestionName(), question.getQuestionType(), question.getSurvey().getSurveyId());

        if (question instanceof ChoiceQuestion) {
            dto = convertChoiceQuestion((ChoiceQuestion) question);
        } else if (question instanceof RangeQuestion) {
            dto = convertRangeQuestion((RangeQuestion) question);
        }

        return dto;
    }

    private ChoiceDto convertChoiceQuestion(ChoiceQuestion question) {
        ChoiceDto choiceDto = new ChoiceDto(question);
        choiceDto.setOptions(question.getOptions().stream()
                .map(option -> new OptionDto(
                        option.getOptionId(),
                        option.getOptionText()))
                .collect(Collectors.toList()));
        choiceDto.setMultiChoice(question.isMultiChoice());
        return choiceDto;
    }

    private RangeDto convertRangeQuestion(RangeQuestion question) {
        RangeDto rangeDto = new RangeDto(question);
        rangeDto.setMin(question.getMin());
        rangeDto.setMax(question.getMax());
        rangeDto.setStep(question.getStep());
        return rangeDto;
    }
}
