package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.user.RegularUserDto;
import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class SignUpApiController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignUpApiController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("{id}")
    ResponseEntity<UserDto> getOneUser(@PathVariable("id") long userId) {
        var user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }
//    /api/organizations/2/orgAdmins
//    /api/organizations/2/supervisors
    @PostMapping
    ResponseEntity<RegularUserDto> addUser(@RequestBody @Valid RegularUserDto userDto) {
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        var createdUser = userService.addUser(
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), encryptedPassword);
        return new ResponseEntity<>(
                modelMapper.map(createdUser, RegularUserDto.class),
                HttpStatus.CREATED
        );
    }
}
