package org.poopoo.twitterclone.repository;

import org.poopoo.twitterclone.entity.Follow;
import org.poopoo.twitterclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
