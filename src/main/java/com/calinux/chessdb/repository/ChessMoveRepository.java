package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessMove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessMoveRepository extends JpaRepository<ChessMove, Long> {
}