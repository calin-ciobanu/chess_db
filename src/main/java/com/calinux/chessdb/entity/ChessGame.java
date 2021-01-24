package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "chess_game",
        indexes = {
                @Index(name = "idx_chess_game_players", columnList = "white_player_id,black_player_id"),
                @Index(name = "idx_chess_game_chess_time_control_id", columnList = "chess_time_control_id"),
                @Index(name = "idx_chess_game_chess_game_result_id", columnList = "chess_game_result_id"),
                @Index(name = "idx_chess_game_pgn_game_id", columnList = "pgn_game_id")
        }
)
@Getter
@Setter
@ToString
public class ChessGame extends AuditableEntity {

    // Fields
    @Column(name = "compressed_moves", columnDefinition = "text")
    private String compressedMoves;

    @Column(name = "number_of_moves", columnDefinition = "integer")
    private Integer numberOfMoves;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // External References
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game_white__user"), name = "white_player_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessUser whitePlayer;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__game_black__game_result"), name = "black_player_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessUser blackPlayer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_game__chess_time_control"), name = "chess_time_control_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessTimeControl chessTimeControl;

    @OneToMany(mappedBy = "chessGame")
    private List<ChessMove> chessMoves;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_game__chess_game_result"), name = "chess_game_result_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
    @NotNull
    private ChessGameResult chessGameResult;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_game__pgn_game"), name = "pgn_game_id", columnDefinition = "bigint", referencedColumnName = "id")
    @NotNull
    private PgnGame pgnGame;
}
