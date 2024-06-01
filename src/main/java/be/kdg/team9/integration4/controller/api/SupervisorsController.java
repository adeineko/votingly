package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.user.RegularUserDto;
import be.kdg.team9.integration4.controller.api.dto.user.SupervisorDto;
import be.kdg.team9.integration4.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addSupervisor")
public class SupervisorsController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public SupervisorsController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    ResponseEntity<SupervisorDto> addSupervisor(@RequestBody @Valid SupervisorDto userDto) {
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        var createdUser = userService.addSupervisor(
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), encryptedPassword);
        return new ResponseEntity<>(
                modelMapper.map(createdUser, SupervisorDto.class),
                HttpStatus.CREATED
        );
    }
}
