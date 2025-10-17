package org.poopoo.twitterclone.controller;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleLike(
        @RequestParam Long postId,
        @RequestParam String username
    ) {
        return ResponseEntity.ok(likeService.toggleLike(postId, username));
    }
}
