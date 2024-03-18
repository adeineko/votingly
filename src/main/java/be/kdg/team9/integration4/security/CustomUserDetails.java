//package be.kdg.team9.integration4.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//
//public class CustomUserDetails extends User {
//    private final long userId;
//
//    public CustomUserDetails(String username,
//                             String password,
//                             Collection<? extends GrantedAuthority> authorities,
//                             long userId) {
//        super(username, password, authorities);
//        this.userId = userId;
//    }
//
//    public long getUserId() {
//        return userId;
//    }
//}
