//package be.kdg.team9.integration4.controller.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class AnswersControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
////    @Test
////    public void shouldSaveAnswerForOpenType() throws Exception {
////        mockMvc.perform(post("/api/answers/open")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(new NewOpenAnswer(
////                                "answer", 1L, 1L, new Question()
////                        ))))
////                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.id").isNumber())
////                .andExpect(jsonPath("$.answer").value("title"))
////                .andReturn();
////    }
//}