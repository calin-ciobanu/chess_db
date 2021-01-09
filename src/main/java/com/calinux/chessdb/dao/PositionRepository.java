package com.calinux.chessdb.dao;

import com.calinux.chessdb.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}