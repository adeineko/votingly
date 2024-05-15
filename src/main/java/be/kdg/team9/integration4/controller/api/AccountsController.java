package be.kdg.team9.integration4.controller.api;

import be.kdg.team9.integration4.controller.api.dto.user.UpdatedUserDto;
import be.kdg.team9.integration4.controller.api.dto.user.UserDto;
import be.kdg.team9.integration4.security.CustomUserDetails;
import be.kdg.team9.integration4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



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
    public ResponseEntity<UserDto> getAuthenticatedUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // User is not authenticated, return 401 Unauthorized
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Extract user ID from the authenticated user's details
        long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        var user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    // "/api/issues/{id}"
    @PatchMapping("{id}")
    ResponseEntity<Void> changeIssue(@PathVariable("id") long userId,
                                     @RequestBody @Valid UpdatedUserDto updatedUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (userService.changeUserInfo(userId, updatedUserDto.getFirstName(), updatedUserDto.getLastName(), updatedUserDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


