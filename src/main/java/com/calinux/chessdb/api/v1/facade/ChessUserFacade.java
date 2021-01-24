package com.calinux.chessdb.api.v1.facade;

import com.calinux.chessdb.api.v1.dto.UserResponseDTO;
import com.calinux.chessdb.api.v1.dto.SignupRequestDTO;
import com.calinux.chessdb.api.v1.dto.mapper.SignupRequestDTOMapper;
import com.calinux.chessdb.api.v1.dto.mapper.LoginResponseDTOMapper;
import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.service.ChessUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChessUserFacade {

    private final ChessUserService chessUserService;

    public UserResponseDTO create(SignupRequestDTO signupRequestDTO) {
        ChessUser chessUser = chessUserService.create(SignupRequestDTOMapper.INSTANCE.toChessUser(signupRequestDTO));

        UserResponseDTO userResponseDTO = LoginResponseDTOMapper.INSTANCE.fromChessUser(chessUser);
        userResponseDTO.setToken("token");
        userResponseDTO.setRefreshToken("refresh_token");

        return userResponseDTO;
    }

    public UserResponseDTO getById(Long id) {
        ChessUser chessUser = chessUserService.getById(id);

        UserResponseDTO userResponseDTO = LoginResponseDTOMapper.INSTANCE.fromChessUser(chessUser);
        userResponseDTO.setToken("token");
        userResponseDTO.setRefreshToken("refresh_token");

        return userResponseDTO;
    }
}
