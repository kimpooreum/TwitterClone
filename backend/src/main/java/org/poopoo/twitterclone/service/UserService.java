package org.poopoo.twitterclone.service;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
