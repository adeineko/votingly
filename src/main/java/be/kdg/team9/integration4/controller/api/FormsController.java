package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.FormDto;
import be.kdg.team9.integration4.controller.api.dto.QuestionDto;
import be.kdg.team9.integration4.model.Form;
import be.kdg.team9.integration4.model.Question;
import be.kdg.team9.integration4.service.FormService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
public class FormsController {
    private FormService formService;
    private ModelMapper modelMapper;

    @Autowired
    public FormsController(FormService formService, ModelMapper modelMapper) {
        this.formService = formService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<FormDto> getAllForms() {
        return formService.getAllForms()
                .stream()
                .map(formDto -> modelMapper.map(formDto, FormDto.class)).toList();
    }


    @GetMapping("/{id}/questions")
    ResponseEntity<List<QuestionDto>> getQuestionsOfForm(@PathVariable("id") long formId) {
        var form = formService.getQuestionOfForm(formId);
        if (form == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(form.getQuestions()
                .stream()
                .map(Question::getForm)
                .map(dev -> modelMapper.map(dev, QuestionDto.class))
                .toList());
    }
}
