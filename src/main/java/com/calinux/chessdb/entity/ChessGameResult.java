package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;
import com.calinux.chessdb.entity.enums.GameResultType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "chess_game_result")
@Getter @Setter
@ToString
public class ChessGameResult extends BaseEntity {

    // Fields
    @Column(name = "result_type")
    @Enumerated(EnumType.STRING)
    private GameResultType resultType;

    // External References
}
