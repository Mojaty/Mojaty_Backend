package com.motivation.mojaty.domain.user.domain;

import com.motivation.mojaty.domain.user.domain.type.Role;
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

    @Enumerated(STRING)
    private Role role;

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    public void encodedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void update(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
