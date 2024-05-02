package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class SignUpApiController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SignUpApiController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id}")
    ResponseEntity<UserDto> getOneIssue(@PathVariable("id") long issueId) {
        var issue = userService.getUserById(issueId);
        if (issue == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(issue, UserDto.class));
    }

//    @PostMapping("/process_register")
//    @ResponseBody
//    public String processRegistration(@RequestBody User user) {
//        userRepository.save(user);
//        return "User registered successfully!";
//    }

//    @PostMapping
//    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
//        User createdUser = userService.addUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }


    @PostMapping
    ResponseEntity<UserDto> addIssue(@RequestBody @Valid UserDto userDto) {
        var createdUser = userService.addUser(
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword());
        return new ResponseEntity<>(
                modelMapper.map(createdUser, UserDto.class),
                HttpStatus.CREATED
        );
    }
}
