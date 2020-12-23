/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:40
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUserId(Long id);
}
