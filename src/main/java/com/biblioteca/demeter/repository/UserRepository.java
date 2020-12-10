/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:36
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmailIgnoreCase(String email);
}
