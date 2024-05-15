package be.kdg.team9.integration4.security;

import be.kdg.team9.integration4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getUserByEmail(username); // Fetch user by email
        if (user != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            // Spring's `User`
            return new CustomUserDetails(
                    user.getEmail(),
                    user.getPassword(),
                    authorities,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName());
        }
        throw new UsernameNotFoundException("User with email " + username + " was not found.");
    }
}