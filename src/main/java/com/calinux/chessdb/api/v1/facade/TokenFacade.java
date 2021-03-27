package com.calinux.chessdb.api.v1.facade;

import com.calinux.chessdb.api.v1.dto.AuthenticatedResponseDTO;
import com.calinux.chessdb.api.v1.dto.mapper.AuthenticatedResponseDTOMapper;
import com.calinux.chessdb.api.v1.dto.mapper.ChessUserResponseDTOMapper;
import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.repository.ChessUserRepository;
import com.calinux.chessdb.security.auth.service.TokenService;
import com.calinux.chessdb.service.ChessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenFacade {

    private final ChessUserService chessUserService;

    private final TokenService tokenService;

    public AuthenticatedResponseDTO getAuthenticatedUserResponse(String username) {
        ChessUser chessUser = chessUserService.getByUsername(username);
        AuthenticatedResponseDTO authenticatedResponseDTO = AuthenticatedResponseDTOMapper.INSTANCE.fromChessUser(chessUser);
        authenticatedResponseDTO.setToken(tokenService.generateAuthToken(chessUser));
        authenticatedResponseDTO.setRefreshToken(tokenService.generateRefreshToken(chessUser));

        return authenticatedResponseDTO;
    }
}
