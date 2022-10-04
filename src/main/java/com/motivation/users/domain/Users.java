package com.motivation.users.domain;

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
public class Users {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(STRING)
    private Job job;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    private Role role;

    @Builder
    public Users(String email, Job job, String password) {
        this.email = email;
        this.job = job;
        this.password = password;
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    public void encodedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
