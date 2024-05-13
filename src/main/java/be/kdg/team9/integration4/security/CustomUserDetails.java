package be.kdg.team9.integration4.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final long userId;
    private final String firstName;

    public CustomUserDetails(String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             long userId,
                             String firstName) {
        super(email, password, authorities);
        this.userId = userId;
        this.firstName = firstName;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }
}
