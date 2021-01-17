package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.PgnGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PgnGameRepository extends JpaRepository<PgnGame, Long> {
}