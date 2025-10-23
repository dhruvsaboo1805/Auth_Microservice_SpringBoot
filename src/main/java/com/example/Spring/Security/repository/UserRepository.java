package com.example.Spring.Security.repository;

import com.example.Spring.Security.entity.User;
import com.example.Spring.Security.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
//    Optional<User> findByUsernameAndPassword(String username, String password);
//    Optional<List<User>> findByRole(RoleType role);
}
