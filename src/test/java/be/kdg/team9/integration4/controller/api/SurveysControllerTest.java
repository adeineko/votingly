package be.kdg.team9.integration4.controller.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.controller.api.dto.survey.SurveyDto;
import be.kdg.team9.integration4.model.SurveyType;
import be.kdg.team9.integration4.model.question.QuestionType;

@SpringBootTest
@AutoConfigureMockMvc
class SurveysControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllSurveysShouldReturnListOfSurveysNames() throws Exception {
        mockMvc.perform(
                get("/api/surveys")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }

    @Test
    public void getQuestionsOfSurveyShouldReturnNoContentIfEmpty() throws Exception {
        mockMvc.perform(
                get("/api/surveys/{id}/questions", 55).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void getQuestionsOfSurveyShouldReturnIsOkWIthQuestions() throws Exception {
        mockMvc.perform(
                        get("/api/surveys/{id}/questions", 3).accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andDo(print());
    }
    
    @Test
    public void shouldSaveSurveyAndQuestions() throws Exception {
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setSurveyName("SurveyName");
        surveyDto.setSurveyType(SurveyType.CIRCULAR);
        surveyDto.setStartDate(Date.valueOf(LocalDate.now()));
        surveyDto.setEndDate(Date.valueOf(LocalDate.now()));
        QuestionDto question1 = new QuestionDto("whats up", QuestionType.OPEN);
        QuestionDto question2 = new QuestionDto("whats down", QuestionType.OPEN);
        List<QuestionDto> questions = List.of(question1,question2);
        surveyDto.setQuestions(questions);
        // mockMvc.perform(post("/api/surveys/create")
        mockMvc.perform(post("/api/surveys")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(surveyDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();
    }

    @Test
    public void getQuestionsOfSurveyShouldReturnIsOkWithQuestions() throws Exception {
        mockMvc.perform(
                        get("/api/surveys/{id}/questions", 4).accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }
}
