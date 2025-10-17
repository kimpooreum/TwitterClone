package org.poopoo.twitterclone.service;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.dto.AuthRequest;
import org.poopoo.twitterclone.dto.AuthResponse;
import org.poopoo.twitterclone.dto.RegisterRequest;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.UserRepository;
import org.poopoo.twitterclone.security.CustomUserDetails;
import org.poopoo.twitterclone.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .nickname(request.getNickname())
            .role("ROLE_USER")
            .build();

        userRepository.save(user);

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
            .token(token)
            .build();
    }
}
