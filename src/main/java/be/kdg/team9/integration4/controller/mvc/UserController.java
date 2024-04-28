//package be.kdg.team9.integration4.controller.mvc;
//
//
//import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
//import be.kdg.team9.integration4.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/{id}")
//    public ModelAndView getOneUser(@PathVariable("id") long userId) {
//        var oneUser = userService.getUserById(userId);
//        var mav = new ModelAndView();
////        mav.setViewName("navbar");
//        mav.addObject("one_user", new UserDto(
//                oneUser.getId(),
//                oneUser.getFirstName(),
//                oneUser.getLastName(),
//                oneUser.getEmail()
//        ));
//        return mav;
//    }
//}
