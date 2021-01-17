package com.calinux.chessdb.repository;

import com.calinux.chessdb.entity.ChessTimeControl;
import com.calinux.chessdb.entity.enums.TimeControlType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChessTimeControlRepository extends JpaRepository<ChessTimeControl, Long> {

    @Query("select ctc from ChessTimeControl ctc where ctc.gameType=?1 and ctc.gameTimeInSeconds=?2 and ctc.incrementPerMoveInSeconds=?3")
    ChessTimeControl findByGameTypeAndGameTimeInSecondsAndIncrementPerMoveInSeconds(
            TimeControlType gameType,
            Integer gameTimeInSeconds,
            Integer incrementPerMoveInSeconds);
}
