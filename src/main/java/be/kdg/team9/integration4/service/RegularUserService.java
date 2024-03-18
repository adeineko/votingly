package be.kdg.team9.integration4.service;

import be.kdg.team9.integration4.model.user.RegularUser;
import be.kdg.team9.integration4.repositories.RegularUserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegularUserService {
    private final RegularUserRepository userRepository;

    public RegularUserService(RegularUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegularUser getUserByName(String name) {
        return userRepository.findByFirstName(name);
    }
}
