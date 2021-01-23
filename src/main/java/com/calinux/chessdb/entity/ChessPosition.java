package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "chess_position",
        indexes = {
                @Index(name = "idx_chess_position_description", columnList = "description"),
                @Index(name = "idx_chess_position_hash", columnList = "hash"),
                @Index(name = "idx_chess_position_game_id", columnList = "chess_game_id"),
                @Index(name = "idx_chess_position_move_id", columnList = "chess_move_id")
        }
)
@Getter
@Setter
@ToString
public class ChessPosition extends BaseEntity {

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
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_position__chess_game"), name = "chess_game_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessGame chessGame;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_position__chess_move"), name = "chess_move_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessMove chessMove;


}
