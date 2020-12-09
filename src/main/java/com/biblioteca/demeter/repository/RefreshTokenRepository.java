/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:42
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}