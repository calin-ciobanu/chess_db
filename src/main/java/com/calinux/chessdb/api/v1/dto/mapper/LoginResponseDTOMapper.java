package com.calinux.chessdb.api.v1.dto.mapper;

import com.calinux.chessdb.api.v1.dto.UserResponseDTO;
import com.calinux.chessdb.entity.ChessUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginResponseDTOMapper {

    LoginResponseDTOMapper INSTANCE = Mappers.getMapper(LoginResponseDTOMapper.class);

    @Mapping(source = "chessUserDetail.about", target = "about")
    UserResponseDTO fromChessUser(ChessUser chessUser);
}
