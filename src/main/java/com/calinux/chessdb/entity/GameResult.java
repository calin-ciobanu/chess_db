package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.BaseEntity;
import com.calinux.chessdb.entity.enums.GameResultType;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "game_result")
public class GameResult extends BaseEntity {

    // Fields
    @Column(name = "result_type")
    @Enumerated(EnumType.STRING)
    @Size(max = 10)
    private GameResultType resultType;

    // External References
}
