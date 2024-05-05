package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.model.Option;
import be.kdg.team9.integration4.model.answers.ChoiceAnswer;
import be.kdg.team9.integration4.model.answers.OpenAnswer;
import be.kdg.team9.integration4.model.answers.RangeAnswer;
import be.kdg.team9.integration4.model.question.ChoiceQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @WithUserDetails("user")
    public void shouldSaveAnswerForOpenType() throws Exception {
        mockMvc.perform(post("/api/answers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(new OpenAnswer(
                                "answer"
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.answer").value("answer"))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithUserDetails("user")
    public void shouldSaveAnswerForRangeType() throws Exception {
        mockMvc.perform(post("/api/answers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(new RangeAnswer(
                                2
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(2))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithUserDetails("user")
    public void shouldSaveAnswerForChoiceType() throws Exception {
        mockMvc.perform(post("/api/answers/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(new ChoiceAnswer(
                               List.of(new Option(1, "option 1", new ChoiceQuestion()))
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.options").isArray())
                .andDo(print())
                .andReturn();
    }
}