package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
