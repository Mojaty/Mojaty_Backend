package com.motivation.mojaty.domain.user.domain;

import com.motivation.mojaty.domain.user.domain.type.Role;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "USERS")
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(STRING)
    private Role role;

    @Builder
    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    public void encodedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void update(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void matchedPassword(PasswordEncoder passwordEncoder, User user, String password) {
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }
}
