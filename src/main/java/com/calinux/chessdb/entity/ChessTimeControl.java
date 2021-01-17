package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;
import com.calinux.chessdb.entity.enums.TimeControlType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "chess_time_control",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_chess_time_control_game_setup",
                        columnNames = {
                                "game_type",
                                "game_time_in_seconds",
                                "increment_per_move_in_seconds"
                        }
                )
        }
)
@Getter
@Setter
@ToString
public class ChessTimeControl extends BaseEntity {

    // Fields
    @Column(name = "game_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TimeControlType gameType;

    @Column(name = "game_time_in_seconds", columnDefinition = "integer", nullable = false)
    @NotNull
    private Integer gameTimeInSeconds;

    @Column(name = "increment_per_move_in_seconds", columnDefinition = "integer", nullable = false)
    @NotNull
    private Integer incrementPerMoveInSeconds;

    // External References

    // Setters / Getters
    public TimeControlType getGameType() {
        if (gameType == null) {
            inferGameType();
        }
        return gameType;
    }

    // Triggers
    @PrePersist
    @PreUpdate
    private void inferGameType() {
        if (gameType == null && gameTimeInSeconds > 0 && incrementPerMoveInSeconds >= 0) {
            if (gameTimeInSeconds < 180) {
                gameType = TimeControlType.BULLET;
            } else if (gameTimeInSeconds < 600) {
                gameType = TimeControlType.BLITZ;
            } else if (gameTimeInSeconds < 1800) {
                gameType = TimeControlType.RAPID;
            } else {
                gameType = TimeControlType.CLASSICAL;
            }
        } else {
            gameType = TimeControlType.UNKNOWN;
        }
    }
}
