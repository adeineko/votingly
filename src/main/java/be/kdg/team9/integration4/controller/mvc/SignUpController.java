package be.kdg.team9.integration4.controller.mvc;

import be.kdg.team9.integration4.model.user.User;
import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.repositories.UserRepository;
import be.kdg.team9.integration4.security.CustomUserDetails;
import be.kdg.team9.integration4.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SignUpController {
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

}

//@Controller
//public class SignUpController {
//    private final UserRepository userRepository;
//    private final UserService userService;
//    private final ModelMapper modelMapper;
//
//    public SignUpController(UserRepository userRepository, UserService userService, ModelMapper modelMapper) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//        this.modelMapper = modelMapper;
//    }
//
//
//    @GetMapping("/signup")
//    public String showSignupPage(Model model) {
//        model.addAttribute("user", new User());
//        return "signup";
//    }
//
////    @PostMapping("/signup")
////    public String processSignupForm(@RequestParam("firstName") String firstName,
////                                    @RequestParam("lastName") String lastName,
////                                    @RequestParam("email") String email,
////                                    @RequestParam("password") String password) {
////        User user = new User();
////        user.setFirstName(firstName);
////        user.setLastName(lastName);
////        user.setEmail(email);
////        user.setPassword(password);
////
////        userRepository.save(user);
////
////        return "redirect:/";
////    }
//
//
//    @PostMapping
//    ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto,
//                                     @AuthenticationPrincipal CustomUserDetails user) {
////        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//        var createdUser = userService.addUser(
//                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword());
//        return new ResponseEntity<>(
//                modelMapper.map(createdUser, UserDto.class),
//                HttpStatus.CREATED
//        );
//    }
//
////    @PostMapping("/process_register")
////    public String processRegister(User user) {
////        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////        String encodedPassword = passwordEncoder.encode(user.getPassword());
////        user.setPassword(encodedPassword);
////
////        userRepository.save(user);
////        return "login";
////    }
//}

