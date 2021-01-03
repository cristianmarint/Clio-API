/*
 * @Author: cristianmarint
 * @Date: 9/12/20 10:08
 */

package com.api.clio.service;

import com.api.clio.exceptions.DemeterException;
import com.api.clio.model.RefreshToken;
import com.api.clio.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Timestamp.from(Instant.now()));

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token){
        refreshTokenRepository
                .findByToken(token)
                .orElseThrow(()-> new DemeterException("Invalid refresh Token RefreshTokenService.java"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
