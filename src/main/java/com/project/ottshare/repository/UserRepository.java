package com.project.ottshare.repository;

import com.project.ottshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
