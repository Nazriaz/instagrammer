package org.nazriaz.instagrammer.repository;

import org.nazriaz.instagrammer.dao.InstagramSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstagramSessionRepo extends JpaRepository<InstagramSession, Integer> {
}
