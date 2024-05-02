package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.user.User;
import be.kdg.team9.integration4.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
