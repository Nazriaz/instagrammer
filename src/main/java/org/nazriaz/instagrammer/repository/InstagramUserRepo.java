package org.nazriaz.instagrammer.repository;

import org.nazriaz.instagrammer.entity.InstagramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstagramUserRepo extends JpaRepository<InstagramUserEntity,Long> {
    InstagramUserEntity findByUsername(String username);
    boolean existsByUsername(String username);
}