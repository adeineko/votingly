package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Accounts page is working but not using button MyAccount
 * To find it add id parameter to the url eg. http/localhost/8080/account/1
 * */

@RestController
@RequestMapping ("/api/account")
public class AccountsController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    public AccountsController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<UserDto> getOneUser(@RequestParam("id") long userId) {
        var oneUser = userService.getUserById(userId);
        if (oneUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(oneUser, UserDto.class));

    }
}


