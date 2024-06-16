package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.answer.AnswerDto;
import be.kdg.team9.integration4.controller.api.dto.answer.NewAnswerDto;
import be.kdg.team9.integration4.model.answers.Answer;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.security.CustomUserDetails;
import be.kdg.team9.integration4.service.AnswerService;
import be.kdg.team9.integration4.service.QuestionService;
import jakarta.validation.Valid;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswersController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswersController(AnswerService answerService, QuestionService questionService, ModelMapper modelMapper) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<AnswerDto> saveAnswerForQuestion(@RequestBody
                                                           @Valid NewAnswerDto newAnswerDto,
                                                           @PathVariable Question questionId,
                                                           @AuthenticationPrincipal CustomUserDetails user) {
        var question = questionService.getQuestion(questionId.getId());
        long userId = (user != null) ? user.getUserId() : 0;

        return switch (question.getQuestionType()) {
            case OPEN -> {
                var createdOpenAnswer = answerService.saveOpen(newAnswerDto.getSurveyId(),
                        userId,
                        questionId,
                        newAnswerDto.getAnswer(),
                        LocalDateTime.now()
                );
                yield new ResponseEntity<>(
                        modelMapper.map(createdOpenAnswer, AnswerDto.class),
                        HttpStatus.CREATED
                );
            }
            case RANGE -> {
                var createdRangeAnswer = answerService.saveRange(question.getSurvey().getSurveyId(),
                        userId,
                        questionId,
                        newAnswerDto.getRange_answer(),
                        LocalDateTime.now());
                yield new ResponseEntity<>(
                        modelMapper.map(createdRangeAnswer, AnswerDto.class),
                        HttpStatus.CREATED
                );
            }
            case CHOICE -> {
                List<AnswerDto> createdChoiceAnswers = new ArrayList<>();
                for (int i = 0; i < newAnswerDto.getOptions_answer().length; i++) {
                    var createdChoiceAnswer = answerService.saveChoice(
                            question.getSurvey().getSurveyId(),
                            userId,
                            questionId,
                            newAnswerDto.getOptions_answer()[i],
                            LocalDateTime.now()
                    );
                    createdChoiceAnswers.add(modelMapper.map(createdChoiceAnswer, AnswerDto.class));
                }
                yield new ResponseEntity<>(
                        createdChoiceAnswers.get(0),
                        HttpStatus.CREATED
                );
            }
            default ->
                    throw new UnsupportedOperationException("Unsupported question type: " + question.getQuestionType());
        };
    }

    @GetMapping(value = "/{surveyId}/export-csv", produces = "text/csv")
    public ResponseEntity<byte[]> exportAnswersToCSV(@PathVariable("surveyId") long surveyId) {
        List<Answer> answersList = answerService.findAllAnswersBySurveyId(surveyId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();


        try (CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader("AnswerID", "SurveyID", "UserID", "QuestionType", "AnswerValue", "AnswerTime"))) {
            for (Answer answer : answersList) {

                String answerType = answer.getClass().getSimpleName();
                switch (answerType) {
                    case "OpenAnswer":
                        OpenAnswer openAnswer = (OpenAnswer) answer;
                        csvPrinter.printRecord(openAnswer.getAnswerId(), openAnswer.getSurveyId(), openAnswer.getUserId(), answerType, openAnswer.getAnswer(), openAnswer.getAnswerTime());
                        break;
                    case "RangeAnswer":
                        RangeAnswer rangeAnswer = (RangeAnswer) answer;
                        csvPrinter.printRecord(rangeAnswer.getAnswerId(), rangeAnswer.getSurveyId(), rangeAnswer.getUserId(), answerType, rangeAnswer.getRange_answer(), rangeAnswer.getAnswerTime());
                        break;
                    case "ChoiceAnswer":
                        ChoiceAnswer choiceAnswer = (ChoiceAnswer) answer;
                        csvPrinter.printRecord(choiceAnswer.getAnswerId(), choiceAnswer.getSurveyId(), choiceAnswer.getUserId(), answerType, choiceAnswer.getoption().getOptionText(), choiceAnswer.getAnswerTime());
                    default:
                        break;
                }
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        byte[] csvBytes = out.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=survey_answers.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }


}