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

import be.kdg.team9.integration4.controller.api.dto.SurveyDto;
import be.kdg.team9.integration4.controller.api.dto.questions.QuestionDto;
import be.kdg.team9.integration4.model.SurveyType;

@SpringBootTest
@AutoConfigureMockMvc
class SurveysControllerTest {
    @Autowired
    private MockMvc mockMvc;
    // private final Logger logger;

    @Autowired
    private ObjectMapper objectMapper;
    // @Autowired
    // public SurveysControllerTest(Logger logger) {
    //     this.logger = logger;
    // }

    // @Test
    // public void getAllSurveysShouldReturnListOfSurveysNames() throws Exception {
    //     // logger.info("Active profiles:");
    //     // logger.info(System.getProperty("spring.profiles.active"));
    //     // logger.info(System.getProperty("SPRING_PROFILES_ACTIVE"));
    //     // System.out.println("Active profiles:");
    //     // System.out.println(System.getProperty("spring.profiles.active"));
    //     // System.out.println(System.getProperty("SPRING_PROFILES_ACTIVE"));

    //     mockMvc.perform(
    //             get("/api/surveys")
    //                     .accept(MediaType.APPLICATION_JSON)
    //     ).andDo(print());
    // }

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
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }
    
    @Test
    public void shouldSaveSurveyAndQuestions() throws Exception {
        // Request request = new Request();
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setSurveyName("SurveyName");
        surveyDto.setSurveyType(SurveyType.CIRCULAR);
        surveyDto.setStartDate(Date.valueOf(LocalDate.now()));
        surveyDto.setEndDate(Date.valueOf(LocalDate.now()));
        QuestionDto questionDto = new QuestionDto();
        List<QuestionDto> questions = List.of(questionDto);
        surveyDto.setQuestions(questions);
        // ObjectMapper objectMapped = objectMapper.
        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(surveyDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();
            // .andExpect(jsonPath("$.range_answer").value(2))
    }
}
