package org.nazriaz.instagrammer.repository;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.entity.InstagramUserSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstagramUserSummaryDtoRepo extends JpaRepository<InstagramUserSummaryDto,Long> {
}
