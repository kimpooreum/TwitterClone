package org.poopoo.twitterclone.repository;

import org.poopoo.twitterclone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
