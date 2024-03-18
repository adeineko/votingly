package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.user.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    RegularUser findByFirstName(String name);
}
