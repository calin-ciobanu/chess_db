package com.calinux.chessdb.security.auth.token;

import com.calinux.chessdb.entity.ChessUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenUtil {

    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_UUID = "uuid";
    public static final String CLAIM_USERNAME_ENCODED = "enc_username";

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.auth.expiration}")
    private Long authTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    public String createAuthToken(ChessUser chessUser) {
        return createToken(chessUser, authTokenExpiration);
    }

    public String createRefreshToken(ChessUser chessUser) {
        return createToken(chessUser, refreshTokenExpiration);
    }

    public Claims extractClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    private String createToken(ChessUser chessUser, Long expiration) {
        Claims claims = Jwts.claims().setSubject(chessUser.getUsername());
        claims.put(CLAIM_ROLE, chessUser.getRole());
        claims.put(CLAIM_UUID, UUID.randomUUID());
        claims.put(CLAIM_USERNAME_ENCODED, passwordEncoder.encode(chessUser.getUsername()));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }



}
