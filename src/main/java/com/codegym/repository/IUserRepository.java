package com.codegym.repository;
import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findUserByUsernameContaining(String username);
}
