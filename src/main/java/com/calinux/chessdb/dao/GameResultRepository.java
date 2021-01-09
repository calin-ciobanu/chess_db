package com.calinux.chessdb.dao;

import com.calinux.chessdb.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}