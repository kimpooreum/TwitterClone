package org.poopoo.twitterclone.service;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.entity.Comment;
import org.poopoo.twitterclone.entity.Post;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.CommentRepository;
import org.poopoo.twitterclone.repository.PostRepository;
import org.poopoo.twitterclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> createComment(String username, Long postId, String content) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = Comment.builder()
            .user(user)
            .post(post)
            .content(content)
            .build();

        commentRepository.save(comment);
        response.put("message", "Comment created successfully");
        return response;
    }
}
