package com.calinux.chessdb.security.auth.filter;

import com.calinux.chessdb.api.v1.dto.AuthenticatedResponseDTO;
import com.calinux.chessdb.api.v1.dto.ErrorResponseDTO;
import com.calinux.chessdb.api.v1.dto.LoginDTO;
import com.calinux.chessdb.api.v1.facade.TokenFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenFacade tokenFacade;

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginDTO loginDTO = new LoginDTO();

        try {
            loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername() == null ? loginDTO.getUsername() : loginDTO.getUsername(),
                loginDTO.getPassword(),
                new ArrayList<>())
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponseDTO.setMessage("Invalid username or password");
        errorResponseDTO.setTimestamp(System.currentTimeMillis());

        addJsonToResponseBody(response, objectMapper.writeValueAsString(errorResponseDTO));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        String username = ((User) auth.getPrincipal()).getUsername();
        AuthenticatedResponseDTO authenticatedResponseDTO = tokenFacade.getAuthenticatedUserResponse(username);

        String json = objectMapper.writeValueAsString(authenticatedResponseDTO);
        response.setStatus(HttpStatus.OK.value());
        addJsonToResponseBody(response, json);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private void addJsonToResponseBody(HttpServletResponse response, String json) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(json.getBytes());
        outputStream.flush();
    }

}
