package com.calinux.chessdb.service;

import com.calinux.chessdb.api.v1.dto.UserResponseDTO;
import com.calinux.chessdb.entity.ChessUser;

public interface ChessUserService {
    ChessUser create(ChessUser toChessUser);

    ChessUser getById(Long id);
}
