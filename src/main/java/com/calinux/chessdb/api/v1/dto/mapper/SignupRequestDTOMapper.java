package com.calinux.chessdb.api.v1.dto.mapper;

import com.calinux.chessdb.api.v1.dto.ChessUserDTO;
import com.calinux.chessdb.api.v1.dto.SignupRequestDTO;
import com.calinux.chessdb.entity.ChessUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SignupRequestDTOMapper {

    SignupRequestDTOMapper INSTANCE = Mappers.getMapper(SignupRequestDTOMapper.class);

    @Mapping(source = "about", target = "chessUserDetail.about")
    ChessUser toChessUser(SignupRequestDTO signupRequestDTO);

}

