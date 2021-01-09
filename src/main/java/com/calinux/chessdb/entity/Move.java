package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;
import com.calinux.chessdb.entity.enums.BoardSides;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "move",
        indexes = {
                @Index(name = "idx_move_game_id", columnList = "game_id")
        }
)
public class Move extends BaseEntity {

    // Fields
    @Column(name = "side", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Size(max = 10)
    @NotNull
    private BoardSides side;

    @Column(name = "description", nullable = false)
    @Size(max = 10)
    @NotNull
    private String description;

    @Column(name = "index_in_game", columnDefinition = "integer", nullable = false)
    @NotNull
    private Integer indexInGame;

    // External References
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__move__game"), name = "game_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private Game game;


}
