package com.shopit.shopit.domain.user.vo;

public record UserProfile(
        String name
) {
    public UserProfile {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name blank");
    }
}