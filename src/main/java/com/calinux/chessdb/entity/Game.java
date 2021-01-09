package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import com.calinux.chessdb.entity.enums.BoardSides;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "game",
        indexes = {
                @Index(name = "idx_game_players", columnList = "white_player_id,black_player_id"),
                @Index(name = "idx_game_time_control_id", columnList = "time_control_id"),
                @Index(name = "idx_game_game_result_id", columnList = "game_result_id")
        }
)
public class Game extends AuditableEntity {

    // Fields
    @Column(name = "compressed_moves", columnDefinition = "text")
    private String compressedMoves;

    @Column(name = "number_of_moves", columnDefinition = "integer")
    private Integer numberOfMoves;

    @Column(name = "winning_side", nullable = false)
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BoardSides winningSide;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // External References
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game_white__user"), name = "white_player_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private User whitePlayer;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game_black__game_result"), name = "black_player_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private User blackPlayer;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game__time_control"), name = "time_control_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private TimeControl timeControl;

    @OneToMany(mappedBy = "game")
    private List<Move> moves;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game__game_result"), name = "game_result_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
    @NotNull
    private GameResult gameResult;
}
