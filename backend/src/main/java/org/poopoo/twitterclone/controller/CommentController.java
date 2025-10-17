package org.poopoo.twitterclone.controller;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(
        @RequestParam String username,
        @RequestParam Long postId,
        @RequestParam String content
    ) {
        return ResponseEntity.ok(commentService.createComment(username, postId, content));
    }
}
