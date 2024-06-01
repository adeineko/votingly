package be.kdg.team9.integration4.converters;

import be.kdg.team9.integration4.controller.api.dto.answer.AnswerDto;
//import be.kdg.team9.integration4.controller.api.dto.answer.ChoiceDto;
import be.kdg.team9.integration4.controller.api.dto.answer.ChoiceDto;
import be.kdg.team9.integration4.controller.api.dto.questions.OptionDto;
import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ChoiceAnswerDtoConverter {
    public AnswerDto convert(Answer answer) {

        AnswerDto dto;
        dto = new AnswerDto(answer.getAnswerId(), answer.getSurveyId(), answer.getUserId(), answer.getQuestion());

        if (answer instanceof ChoiceAnswer) {
            dto = convertChoiceAnswer((ChoiceAnswer) answer);
        }
        return dto;
    }

    private ChoiceDto convertChoiceAnswer(ChoiceAnswer answer) {
        ChoiceDto choiceDto = new ChoiceDto(answer);
        choiceDto.setOptions_answer(answer.getOptions_answer().stream()
                .map(option -> new OptionDto(
                        option.getOptionId(),
                        option.getOptionText()))
                .collect(Collectors.toList()));
        return choiceDto;
    }
}
