/*
 * @Author: cristianmarint
 * @Date: 5/11/20 11:40
 */

package com.biblioteca.demeter.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    void deleteConfirmationToken(Long id){
        confirmationTokenRepository.deleteById(id);
    }

    Optional<ConfirmationToken> findConfirmationTokenByToken(String token){
        return confirmationTokenRepository.findByConfirmationTokenByConfirmationToken(token);
    }
}
