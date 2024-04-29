package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.model.User;
import be.kdg.team9.integration4.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

//    @PostMapping("/signup")
//    public String processSignupForm(@RequestParam("firstName") String firstName,
//                                    @RequestParam("lastName") String lastName,
//                                    @RequestParam("email") String email,
//                                    @RequestParam("password") String password) {
//        User user = new User();
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setPassword(password);
//
//        userRepository.save(user);
//
//        return "redirect:/";
//    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "login";
    }
}
