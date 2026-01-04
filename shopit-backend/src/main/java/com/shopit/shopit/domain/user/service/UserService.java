package com.shopit.shopit.domain.user.service;

import com.shopit.shopit.domain.user.entity.User;
import com.shopit.shopit.domain.user.exception.DuplicateEmailException;
import com.shopit.shopit.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(String email, String rawPassword, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.create(
                email,
                encodedPassword,
                name
        );
        userRepository.save(user);
        return user.getId();
    }
}
