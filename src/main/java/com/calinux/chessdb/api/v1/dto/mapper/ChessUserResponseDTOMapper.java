package com.calinux.chessdb.api.v1.dto.mapper;

import com.calinux.chessdb.api.v1.dto.ChessUserDTO;
import com.calinux.chessdb.api.v1.dto.SignupResponseDTO;
import com.calinux.chessdb.entity.ChessUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChessUserResponseDTOMapper {

    ChessUserResponseDTOMapper INSTANCE = Mappers.getMapper(ChessUserResponseDTOMapper.class);

    @Mapping(source = "chessUserDetail.about", target = "about")
    SignupResponseDTO fromChessUser(ChessUser chessUser);
}
