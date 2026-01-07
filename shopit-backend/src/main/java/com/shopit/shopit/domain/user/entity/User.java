package com.shopit.shopit.domain.user.entity;

import com.shopit.shopit.domain.user.vo.UserProfile;
import com.shopit.shopit.type.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(0)"
    )
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static User create(String email, String password, String name) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;
        user.role = UserRole.USER;
        return user;
    }

    public void updateProfile(UserProfile profile) {
        this.name = profile.name();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
