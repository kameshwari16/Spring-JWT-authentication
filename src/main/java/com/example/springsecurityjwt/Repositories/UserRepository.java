package com.example.springsecurityjwt.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurityjwt.Entities.Role;
import com.example.springsecurityjwt.Entities.User;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
    User findByRole(Role role);
    Optional<User> findById(Long id);
}