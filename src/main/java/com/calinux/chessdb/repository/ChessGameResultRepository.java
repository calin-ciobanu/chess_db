package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessGameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessGameResultRepository extends JpaRepository<ChessGameResult, Long> {
}