package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByFirstName(String firstName);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(String email);
}
