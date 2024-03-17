package be.kdg.team9.integration4.security;

import be.kdg.team9.integration4.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final RegularUserService userService;

    public CustomUserDetailsService(RegularUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getUserByName(username);
        if (user != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            // Spring's `User`
            return new User(
                    user.getFirstName(),
                    user.getPassword(),
                    authorities);
        }
        throw new UsernameNotFoundException("User with" + username + " was not found.");
    }
}
