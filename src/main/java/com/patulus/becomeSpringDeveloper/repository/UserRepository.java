package com.patulus.becomeSpringDeveloper.repository;

import com.patulus.becomeSpringDeveloper.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
}
