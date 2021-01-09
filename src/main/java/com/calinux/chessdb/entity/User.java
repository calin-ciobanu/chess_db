package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "user",
        indexes = {
                @Index(name = "idx_user_name", columnList = "first_name, last_name, username"),
                @Index(name = "idx_user_email", columnList = "email", unique = true),
                @Index(name = "idx_user_user_detail_id", columnList = "user_detail_id"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_username", columnNames = "username")
        }
)
public class User extends AuditableEntity {

    // Fields
    @Column(name = "first_name", nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String lastName;

    @Column(name = "username", nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    // External References
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__user__user_detail"), name = "user_detail_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private UserDetail userDetail;
}
