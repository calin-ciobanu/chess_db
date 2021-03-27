package com.calinux.chessdb.service;

import com.calinux.chessdb.entity.ChessUser;
import com.calinux.chessdb.entity.enums.ChessUserRole;

public interface ChessUserService {
    ChessUser create(ChessUser toChessUser, ChessUserRole chessUserRole);

    ChessUser getById(Long id);

    ChessUser getByUsername(String username);
}
