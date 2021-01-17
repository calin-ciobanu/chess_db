package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPositionRepository extends JpaRepository<ChessPosition, Long> {
}