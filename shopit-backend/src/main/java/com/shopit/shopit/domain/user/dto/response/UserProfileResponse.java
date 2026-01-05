package com.shopit.shopit.domain.user.dto.response;

import com.shopit.shopit.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private Long userId;
    private String email;
    private String name;

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
