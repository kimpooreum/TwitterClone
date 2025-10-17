package org.poopoo.twitterclone.service;

import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.entity.Follow;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.FollowRepository;
import org.poopoo.twitterclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> toggleFollow(String followerEmail, String followingEmail) {
        Map<String, Object> response = new HashMap<>();

        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findByEmail(followingEmail)
            .orElseThrow(() -> new RuntimeException("Following not found"));

        Optional<Follow> existing = followRepository.findByFollowerAndFollowing(follower, following);

        if (existing.isPresent()) {
            followRepository.delete(existing.get());
            response.put("message", "Unfollowed successfully");
        } else {
            Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
            followRepository.save(follow);
            response.put("message", "Followed successfully");
        }

        return response;
    }
}
