package com.calinux.chessdb.security.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTAuthorizationFilter { // extends BasicAuthenticationFilter {

//    private final TokenService tokenService;
//
//    private final ObjectMapper objectMapper;
//
//    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
//                                  TokenService tokenService, ObjectMapper objectMapper) {
//        super(authenticationManager);
//        this.tokenService = tokenService;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (isAuthorizationHeaderValid(header)) {
//            try {
//                String tokenValue = tokenService.extractTokenValue(request);
//                Claims claims = tokenService.getTokenClaims(tokenValue);
//                String username = claims.getSubject();
//
//                Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority((String) claims.get(tokenService.JWT_ROLES_CLAIMS_FIELD)));
//
//                if (tokenService.isTokenValid(tokenValue)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, tokenService.getDefaultCredentials(), authorities);
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                } else {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.getWriter().write(objectMapper.writeValueAsString(new RestError(JwtErrorMessage.INVALID)));
//
//                    return;
//                }
//            } catch (ExpiredJwtException ex) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                response.getWriter().write(objectMapper.writeValueAsString(new RestError(JwtErrorMessage.EXPIRED)));
//
//                return;
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private boolean isAuthorizationHeaderValid(String header) {
//        return header != null && header.startsWith(tokenService.AUTHORIZATION_TOKEN_PREFIX);
//    }

}
