package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.user.RegularUser;
import be.kdg.team9.integration4.model.user.Supervisor;
import be.kdg.team9.integration4.model.user.User;
import be.kdg.team9.integration4.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String name) {
        return userRepository.findByFirstName(name);
    }

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
//
//    @Transactional
//    public User addUser(String firstName, String lastName, String email, String password){
////        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
////        String encodedPassword = passwordEncoder.encode(password);
//        var user = new User(firstName, lastName, email, password);
//        var savedUser = userRepository.save(user);
//        return savedUser;
//    }

    public RegularUser addUser(String firstName, String lastName, String email, String password) {
        RegularUser user = new RegularUser(); // Create an instance of RegularUser
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public Supervisor addSupervisor(String firstName, String lastName, String email, String password) {
        Supervisor user = new Supervisor();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType("SUPERVISOR");

        return userRepository.save(user);
    }

    public boolean changeUserInfo(long issueId, String newFirstName, String newLastName, String newEmail) {
        var user = userRepository.findById(issueId);
        if (user == null) {
            return false;
        }
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setEmail(newEmail);
        userRepository.save(user);
        return true;
    }
}
