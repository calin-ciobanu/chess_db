package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "position",
        indexes = {
                @Index(name = "idx_position_description", columnList = "description"),
                @Index(name = "idx_position_hash", columnList = "hash"),
                @Index(name = "idx_position_game_id", columnList = "game_id"),
                @Index(name = "idx_position_move_id", columnList = "move_id")
        }
)
public class Position extends BaseEntity {

    // Fields
    @Column(name = "description", nullable = false)
    @Size(min = 64, max = 64)
    @NotNull
    private Byte[] description;

    @Column(name = "hash", nullable = false)
    @NotNull
    private String hash;

    // External References
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__position__game"), name = "game_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private Game game;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__position__move"), name = "move_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private Move move;


}
