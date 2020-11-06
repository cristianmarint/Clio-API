/*
 * @Author: cristianmarint
 * @Date: 5/11/20 10:07
 */

package com.biblioteca.demeter.user;

import com.biblioteca.demeter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}