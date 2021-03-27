package com.calinux.chessdb.security.auth.service;

import com.calinux.chessdb.entity.ChessUser;

public interface TokenService {
    String generateAuthToken(ChessUser chessUser);

    String generateRefreshToken(ChessUser chessUser);

    void invalidateSession(String token);

    void invalidateAllSessions(String username);
}
