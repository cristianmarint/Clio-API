/*
 * @Author: cristianmarint
 * @Date: 9/12/20 10:08
 */

package com.biblioteca.demeter.service;

import com.biblioteca.demeter.exceptions.DelimiterException;
import com.biblioteca.demeter.model.RefreshToken;
import com.biblioteca.demeter.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token){
        refreshTokenRepository
                .findByToken(token)
                .orElseThrow(()-> new DelimiterException("Invalid refresh Token RefreshTokenService.java"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
