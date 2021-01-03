/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:42
 */

package com.api.clio.repository;

import com.api.clio.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}