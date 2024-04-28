//package be.kdg.team9.integration4.controller.api;
//
//import be.kdg.team9.integration4.controller.api.dto.OptionDto;
//import be.kdg.team9.integration4.controller.api.dto.QuestionDto;
//import be.kdg.team9.integration4.model.ChoiceQuestion;
//import be.kdg.team9.integration4.service.OptionService;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/option")
//public class OptionController {
//    private final OptionService optionService;
//    private final ModelMapper modelMapper;
//
//    public OptionController(OptionService optionService, ModelMapper modelMapper) {
//        this.optionService = optionService;
//        this.modelMapper = modelMapper;
//    }
//
////    @GetMapping("/{id}")
////    ResponseEntity<List<OptionDto>> getOptionsOfQuestion(@PathVariable("id") long questionId){
////        List<OptionDto> optionDtos = optionService.getOptionsOfQuestion(questionId)
////                .stream()
////                .map(option -> modelMapper.map(option, OptionDto.class))
////                .toList();
////
////        return ResponseEntity.ok(optionDtos);
////    }
//}
