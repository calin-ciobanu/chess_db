package com.calinux.chessdb.dao;

import com.calinux.chessdb.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<Move, Long> {
}