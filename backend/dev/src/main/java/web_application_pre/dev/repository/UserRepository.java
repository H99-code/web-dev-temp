package web_application_pre.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web_application_pre.dev.entity.User;


import java.util.Optional;




public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}



