package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "time_control",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_time_control_game_setup",
                        columnNames = {
                                "game_type",
                                "game_time_in_seconds",
                                "increment_per_move_in_seconds"
                        }
                ),
                @UniqueConstraint(name = "unique_time_control_name", columnNames = "name")
        }
)
public class TimeControl extends BaseEntity {

    // Fields
    @Column(name = "name", nullable = false)
    @Size(max = 10)
    @NotNull
    private String name;

    @Column(name = "game_type", nullable = false)
    @Size(max = 10)
    @NotNull
    private String gameType;

    @Column(name = "game_time_in_seconds", columnDefinition = "integer", nullable = false)
    @NotNull
    private Integer gameTimeInSeconds;

    @Column(name = "increment_per_move_in_seconds", columnDefinition = "integer", nullable = false)
    @NotNull
    private Integer incrementPerMoveInSeconds;

    // External References
}
