package blog.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.backend.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
