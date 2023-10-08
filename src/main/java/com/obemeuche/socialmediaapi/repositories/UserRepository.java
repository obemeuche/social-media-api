package com.obemeuche.socialmediaapi.repositories;

import com.obemeuche.socialmediaapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    Optional<User> getUserById(Long id);
}
