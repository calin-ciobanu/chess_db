package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "pgn_game",
        indexes = {
                @Index(name = "idx_pgn_game", columnList = "last_name,moves"),
                @Index(name = "idx_pgn_game_processed", columnList = "processed")
        }
)
@Getter
@Setter
@ToString
public class PgnGame extends AuditableEntity {

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "moves", columnDefinition = "text", nullable = false)
    @NotNull
    private String moves;

    @Column(name = "pgn_text", columnDefinition = "text", nullable = false)
    @NotNull
    private String pgnText;

    @Column(name = "processed")
    private Boolean processed;
}
