package com.calinux.chessdb.security.auth.service;

import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.entity.InvalidSession;
import com.calinux.chessdb.repository.InvalidSessionRepository;
import com.calinux.chessdb.security.auth.token.TokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenUtil tokenUtil;
    private final InvalidSessionRepository invalidSessionRepository;

    @Override
    public String generateAuthToken(ChessUser chessUser) {
        return tokenUtil.createAuthToken(chessUser);
    }

    @Override
    public String generateRefreshToken(ChessUser chessUser) {
        return tokenUtil.createRefreshToken(chessUser);
    }

    @Override
    public void invalidateSession(String token) {
        Claims claims = tokenUtil.extractClaimsFromToken(token);
        String tokenUUID = (String) claims.get(TokenUtil.CLAIM_UUID);

        InvalidSession invalidSession = new InvalidSession();
        invalidSession.setSessionUuid(tokenUUID);
        invalidSession.setExpiresAt(
                System.currentTimeMillis() + tokenUtil.getRefreshTokenExpiration()
        );

        invalidSessionRepository.save(invalidSession);
    }

    @Override
    public void invalidateAllSessions(String token) {
        Claims claims = tokenUtil.extractClaimsFromToken(token);
        String username = (String) claims.get(TokenUtil.CLAIM_USERNAME_ENCODED);

        InvalidSession invalidSession = new InvalidSession();
        invalidSession.setUsername(username);
        invalidSession.setExpiresAt(
                System.currentTimeMillis() + tokenUtil.getRefreshTokenExpiration()
        );

        invalidSessionRepository.save(invalidSession);
    }


}
