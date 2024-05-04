package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.answer.AnswerDto;
import be.kdg.team9.integration4.controller.api.dto.answer.NewAnswerDto;
import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import be.kdg.team9.integration4.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.model.question.QuestionType;
import be.kdg.team9.integration4.service.AnswerService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AnswersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//     TODO: Add test for saving answer for open type
//     @Test
//     @WithUserDetails("user")
//     public void shouldSaveAnswerForOpenType() throws Exception {
//         mockMvc.perform(post("/api/answers/2")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .accept(MediaType.APPLICATION_JSON)
//                         .with(csrf())
//                         .content(objectMapper.writeValueAsString(new OpenAnswer(
//                                 "answer"
//                         ))))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.answer").value("answer"))
//                 .andDo(print())
//                 .andReturn();
//     }

//     TODO: Add test for saving answer for range type
//     @Test
//     @WithUserDetails("user")
//     public void shouldSaveAnswerForRangeType() throws Exception {
//         mockMvc.perform(post("/api/answers/1")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .accept(MediaType.APPLICATION_JSON)
//                         .with(csrf())
//                         .content(objectMapper.writeValueAsString(new RangeAnswer(
//                                 "2"
//                         ))))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.number").value(2))
//                 .andReturn();
//     }

// TODO: Add test for saving answer for choice type
//     @Test
//     @WithUserDetails("user")
//     public void shouldSaveAnswerForChoiceType() throws Exception {
//         mockMvc.perform(post("/api/answers/5")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .accept(MediaType.APPLICATION_JSON)
//                         .with(csrf())
//                         .content(objectMapper.writeValueAsString(new ChoiceAnswer(
//                                List.of(new Option())
//                         ))))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.options").isArray())
//                 .andReturn();
//     }

// TODO: Add test for saving answer for multi-choice type
//     @Test
//     @WithUserDetails("user")
//     public void shouldSaveAnswerForMultiChoiceType() throws Exception {
//         // Create a NewAnswerDto for multi-choice question
//         NewAnswerDto newAnswerDto = new NewAnswerDto();
//         newAnswerDto.setSurveyId(123); // Set appropriate surveyId
//         newAnswerDto.setOptions(List.of(new Option(1, "option1"), new Option(2, "option2"))); // Add multiple options

//         // Send POST request to save multi-choice answer
//         mockMvc.perform(post("/api/answers/{questionId}", 5) // Assuming 5 is the questionId for multi-choice
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .accept(MediaType.APPLICATION_JSON)
//                         .with(csrf())
//                         .content(objectMapper.writeValueAsString(newAnswerDto)))
//                 .andExpect(status().isCreated())
//                 .andDo(print()); // Assert that the response status is CREATED

//     }
}
