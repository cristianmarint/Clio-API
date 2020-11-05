/*
 * @Author: cristianmarint
 * @Date: 5/11/20 11:38
 */

package com.biblioteca.demeter.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationTokenByConfirmationToken(String token);
}
