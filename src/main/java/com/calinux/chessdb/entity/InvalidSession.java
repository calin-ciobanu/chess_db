package com.calinux.chessdb.entity;

import com.calinux.chessdb.entity.base.AuditableEntity;
import com.calinux.chessdb.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "invalid_session",
        indexes = {
                @Index(name = "idx_invalid_session_username_session_uuid", columnList = "username,session_uuid")
        }
)
@Getter
@Setter
@ToString
public class InvalidSession extends AuditableEntity {

    // Fields
    @Column(name = "username")
    private String username;

    @Column(name = "session_uuid")
    private String sessionUuid;

    @Column(name = "expires_at")
    private Long expiresAt;

    public void setUsername(String username) {
        this.username = new BCryptPasswordEncoder().encode(username);
    }
}
