package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;


    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String getAccountPage() {
        return "account";
    }

    @GetMapping  // Updated mapping to include the full path
    public ModelAndView getOneUser(@RequestParam("id") long id) {
        var oneUser = userService.getUserById(id);
        var mav = new ModelAndView();
        mav.setViewName("navbar");
        mav.addObject("account", new UserDto(
                oneUser.getId(),
                oneUser.getFirstName(),
                oneUser.getLastName(),
                oneUser.getEmail()
        ));
        mav.setViewName("account");
        return mav;
    }
}

