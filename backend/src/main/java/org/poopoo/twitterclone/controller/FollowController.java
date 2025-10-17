package org.poopoo.twitterclone.controller;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleFollow(
        @RequestParam String followerUsername,
        @RequestParam String username
    ) {
        return ResponseEntity.ok(followService.toggleFollow(followerUsername, username));
    }
}
