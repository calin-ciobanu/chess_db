package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "user_detail",
        indexes = {
                @Index(name = "idx_user_detail_rating", columnList = "rating")
        }
)
@Getter
@Setter
@ToString
public class UserDetail extends AuditableEntity {

    // Fields
    @Lob
    @Column(name = "about")
    @Size(max = 512)
    private String about;

    @Column(name = "rating", columnDefinition = "integer")
    private Integer rating;

    // External References
}
