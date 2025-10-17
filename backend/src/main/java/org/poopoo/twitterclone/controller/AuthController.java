package org.poopoo.twitterclone.controller;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.dto.AuthRequest;
import org.poopoo.twitterclone.dto.AuthResponse;
import org.poopoo.twitterclone.dto.RegisterRequest;
import org.poopoo.twitterclone.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
