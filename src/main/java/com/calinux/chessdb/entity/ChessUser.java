package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "chess_user",
        indexes = {
                @Index(name = "idx_chess_user_name", columnList = "first_name, last_name, username"),
                @Index(name = "idx_chess_user_email", columnList = "email", unique = true),
                @Index(name = "idx_chess_user_chess_user_detail_id", columnList = "chess_user_detail_id"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_chess_user_username", columnNames = "username"),
                @UniqueConstraint(name = "unique_chess_user_email", columnNames = "email")
        }
)
@Getter
@Setter
@ToString
public class ChessUser extends AuditableEntity {

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

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    // External References
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk__chess_user__chess_user_detail"), name = "chess_user_detail_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false)
    @NotNull
    private ChessUserDetail chessUserDetail;

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
