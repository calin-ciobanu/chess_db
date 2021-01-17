package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessGame;
import com.calinux.chessdb.entity.PgnGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessGameRepository extends JpaRepository<ChessGame, Long> {
    @Query("select game from ChessGame game where game.pgnGame = ?1")
    ChessGame findByPGNGame(PgnGame pgnGame);
}
