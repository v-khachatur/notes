package com.disqo.notes.business.user.control;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.disqo.notes.business.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r WHERE u.email = ?1")
    Optional<User> findUserWithRolesByEmail(String email);

}
