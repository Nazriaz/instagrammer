package org.nazriaz.instagrammer.service;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.Util.InstagramUserSummaryToDtoConverter;
import org.nazriaz.instagrammer.entity.InstagramUserSummaryDto;
import org.nazriaz.instagrammer.repository.InstagramUserSummaryDtoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstagramUserSummaryService {
    @Autowired
    InstagramUserSummaryDtoRepo instagramUserSummaryDtoRepo;
    public String saveToDb(List<InstagramUserSummary> followers){
        List<InstagramUserSummaryDto> collect = followers.stream().map(InstagramUserSummaryToDtoConverter::convert).collect(Collectors.toList());
        instagramUserSummaryDtoRepo.saveAll(collect);
        return "saved";
    }
}
