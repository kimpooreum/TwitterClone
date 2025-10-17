package org.poopoo.twitterclone.service;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.entity.Like;
import org.poopoo.twitterclone.entity.Post;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.LikeRepository;
import org.poopoo.twitterclone.repository.PostRepository;
import org.poopoo.twitterclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> toggleLike(Long postId, String username) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> existing = likeRepository.findByPostIdAndUserId(postId, user.getId());

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            post.decreaseLikeCount();
            response.put("message", "Like removed");
        } else {
            Like like = Like.builder()
                .post(post)
                .user(user)
                .build();
            likeRepository.save(like);
            post.increaseLikeCount();
            response.put("message", "Like added");
        }

        response.put("likeCount", post.getLikeCount());
        return response;
    }
}
