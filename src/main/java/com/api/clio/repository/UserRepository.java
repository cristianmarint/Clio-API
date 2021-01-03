/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:36
 */

package com.api.clio.repository;

import com.api.clio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmailIgnoreCase(String email);
    User findByUsernameIgnoreCase(String username);
}
