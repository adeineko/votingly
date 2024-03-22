package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.user.RegularUser;
import be.kdg.team9.integration4.model.user.User;
import be.kdg.team9.integration4.repositories.RegularUserRepository;
import be.kdg.team9.integration4.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final RegularUserRepository regularUserRepository;
    private final UserRepository userRepository;

    public UserService(RegularUserRepository regularUserRepository, UserRepository userRepository) {
        this.regularUserRepository = regularUserRepository;
        this.userRepository = userRepository;
    }

    public RegularUser getUserByName(String name) {
        return regularUserRepository.findByFirstName(name);
    }

    public User getUserById(long id) {
        return userRepository.findById(id);
    }
}
