package com.obemeuche.socialmediaapi.repositories;

import com.obemeuche.socialmediaapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
