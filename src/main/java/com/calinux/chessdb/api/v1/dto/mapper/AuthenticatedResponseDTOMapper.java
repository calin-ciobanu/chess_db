package com.calinux.chessdb.api.v1.dto.mapper;

import com.calinux.chessdb.api.v1.dto.AuthenticatedResponseDTO;
import com.calinux.chessdb.api.v1.dto.ChessUserDTO;
import com.calinux.chessdb.entity.ChessUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticatedResponseDTOMapper {

    AuthenticatedResponseDTOMapper INSTANCE = Mappers.getMapper(AuthenticatedResponseDTOMapper.class);

    @Mapping(source = "chessUserDetail.about", target = "about")
    AuthenticatedResponseDTO fromChessUser(ChessUser chessUser);
}
