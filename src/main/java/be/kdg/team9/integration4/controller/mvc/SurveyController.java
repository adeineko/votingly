package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import be.kdg.team9.integration4.service.SurveyService;
import be.kdg.team9.integration4.service.QuestionService;

import be.kdg.team9.integration4.controller.mvc.viewmodel.SurveyViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;
    private final QuestionService questionService;

    public SurveyController(SurveyService surveyService, QuestionService questionService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
    }

    @GetMapping
    public ModelAndView getAllSurveys() {
        final ModelAndView modelAndView = new ModelAndView("surveys");
        final List<Survey> surveys = surveyService.getAllSurveys();
        final List<SurveyViewModel> viewModels = surveys.stream().map(SurveyViewModel::fromDomain).toList();
        modelAndView.addObject("surveys", viewModels);
        return modelAndView;
    }

    @GetMapping("/{id}/questions")
    public String getSurvey(@PathVariable("id") long id, Model model) {
        Survey survey = surveyService.getSurvey(id);
        List<Long> questionIds = surveyService.getQuestionsOfSurvey(id);

        // Add the survey and question IDs to the model
        model.addAttribute("survey", survey);
        model.addAttribute("questionIds", questionIds);
        return "survey";
    }

    @GetMapping("/add")
    public String addSurvey() {
        return "addsurvey";
    }

    @GetMapping("/{id}/details")
    public String surveyDetails(@PathVariable("id") long id, Model model) {
        final Survey survey = surveyService.getSurvey(id);
        final List<Question> questions = questionService.getQuestionsBySurvey(survey);
        survey.setQuestions(questions);
        final SurveyViewModel viewModel = SurveyViewModel.fromDomain(survey);
        model.addAttribute("survey", viewModel);
        return "surveydetails";
    }

    @GetMapping("/{id}/supervisor")
    public String surveySupervisor(@PathVariable("id") long id, Model model) {
        model.addAttribute("surveyId", id);
        return "supervisorprojects";
    }

    @GetMapping("/{surveyId}/statistics")
    public String statistics(@PathVariable("surveyId") long id, Model model) {
        Survey survey = surveyService.getSurvey(id);
        model.addAttribute("survey", survey);
        return "statistics";
    }
}
