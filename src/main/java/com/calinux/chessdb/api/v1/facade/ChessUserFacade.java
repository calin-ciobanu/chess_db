package com.calinux.chessdb.api.v1.facade;

import com.calinux.chessdb.api.v1.dto.ChessUserDTO;
import com.calinux.chessdb.api.v1.dto.SignupRequestDTO;
import com.calinux.chessdb.api.v1.dto.SignupResponseDTO;
import com.calinux.chessdb.api.v1.dto.mapper.ChessUserResponseDTOMapper;
import com.calinux.chessdb.api.v1.dto.mapper.SignupRequestDTOMapper;
import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.entity.enums.ChessUserRole;
import com.calinux.chessdb.security.auth.service.TokenService;
import com.calinux.chessdb.service.ChessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChessUserFacade {

    private final ChessUserService chessUserService;

    private final TokenService tokenService;

    public SignupResponseDTO create(SignupRequestDTO signupRequestDTO) {
        ChessUser chessUser = chessUserService.create(SignupRequestDTOMapper.INSTANCE.toChessUser(signupRequestDTO), ChessUserRole.USER);
        SignupResponseDTO signupResponseDTO = ChessUserResponseDTOMapper.INSTANCE.fromChessUser(chessUser);
        signupResponseDTO.setToken(tokenService.generateAuthToken(chessUser));
        signupResponseDTO.setRefreshToken(tokenService.generateRefreshToken(chessUser));
        return signupResponseDTO;
    }

    public ChessUserDTO getById(Long id) {
        ChessUser chessUser = chessUserService.getById(id);
        ChessUserDTO authenticatedResponseDTO = ChessUserResponseDTOMapper.INSTANCE.fromChessUser(chessUser);
        return authenticatedResponseDTO;
    }
}
