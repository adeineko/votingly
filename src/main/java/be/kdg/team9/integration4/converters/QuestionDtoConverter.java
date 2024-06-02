package be.kdg.team9.integration4.converters;

import be.kdg.team9.integration4.controller.api.dto.questions.OptionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.ChoiceDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDtoIn;
import be.kdg.team9.integration4.controller.api.dto.questions.RangeDto;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import be.kdg.team9.integration4.model.question.OpenQuestion;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.QuestionType;
import be.kdg.team9.integration4.model.question.RangeQuestion;
import be.kdg.team9.integration4.service.OptionService;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class QuestionDtoConverter {
    // private final OptionService optionService;

    // @Autowired
    // public QuestionDtoConverter(OptionService optionService) {
    //     this.optionService = optionService;
    // }

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

    public Question convertFromDtoIn(QuestionDtoIn dto, Survey survey) {
        Question question;
        if (dto.getQuestionType().equals(QuestionType.CHOICE)) {
            List<Option> options = dto.getOptions().stream()
                    .map(this::convertOptionFromDto)
                    .toList();
            question = new ChoiceQuestion(dto.getQuestionName(), dto.getQuestionType(), dto.isMultiChoice(), options);
        } else if (dto.getQuestionType().equals(QuestionType.RANGE)) {
            question = new RangeQuestion(dto.getQuestionName(), dto.getQuestionType(), dto.getMin(), dto.getMax(), dto.getStep());
        } else {
            question = new OpenQuestion(dto.getQuestionName(), dto.getQuestionType());
        }
        question.setSurvey(survey);
        return question;
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

    private Option convertOptionFromDto(OptionDto optionDto) {
        Option option = new Option(optionDto.getOptionText());
        // optionService.addOption(option);
        return option;
    }
}
