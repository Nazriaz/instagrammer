package org.nazriaz.instagrammer.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUnfollowRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.Util.InstagramUserConverter;
import org.nazriaz.instagrammer.Util.InstagramUserSummaryToDtoConverter;
import org.nazriaz.instagrammer.Util.RandomDelayUtill;
import org.nazriaz.instagrammer.entity.InstagramUserEntity;
import org.nazriaz.instagrammer.entity.InstagramUserSummaryDto;
import org.nazriaz.instagrammer.repository.InstagramUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstagramUserService {
    @Autowired
    InstagramUserRepo instagramUserRepo;
    List<InstagramUserSummary> followers = new ArrayList<>();
    @Autowired
    LoginService loginService;

    public InstagramUser getUserFromUserName(String username) throws IOException {
        InstagramUserEntity byUsername = instagramUserRepo.findByUsername(username);
        if (byUsername == null) {
            InstagramSearchUsernameResult instagramSearchUsernameResult = loginService.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
            InstagramUser user = instagramSearchUsernameResult.getUser();
            System.out.println("---LOADED FROM INTERNET---");
            instagramUserRepo.save(InstagramUserConverter.convert(user));
            System.out.println("---SAVED TO DB---");
            return user;
        } else {
            System.out.println("---LOADED FROM DB---");
            return InstagramUserConverter.convertBack(byUsername);
        }
    }

    public InstagramUserEntity getInstagramUserEntityFromDb(String username) {
        return instagramUserRepo.findByUsername(username);
    }

    public List<InstagramUserSummary> searchUserFollowers(String username) throws IOException, InterruptedException {
        InstagramUser instagramUser = getUserFromUserName(username);
        Instagram4j instagram = loginService.getInstagram4j();
        followers = new ArrayList<>();
        InstagramGetUserFollowersResult followersResult = null;
        String maxId = null;

        while (followersResult == null || maxId != null) {
            try {
                followersResult = instagram.sendRequest(new
                        InstagramGetUserFollowersRequest(instagramUser.pk, maxId));
                if (followersResult != null) {
                    if (followersResult.getStatus().equals("ok")) {
                        maxId = followersResult.getNext_max_id();
                        followers.addAll(followersResult.getUsers());
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                Thread.sleep(5000);
            }
        }
        return this.followers;
    }

    public void saveUserAndFollowersToDb(String username) throws IOException {
        InstagramUser instagramUser = getUserFromUserName(username);
        InstagramUserEntity instagramUserEntity = InstagramUserConverter.convert(instagramUser);
        List<InstagramUserSummaryDto> collect = followers.stream().map(InstagramUserSummaryToDtoConverter::convert).collect(Collectors.toList());
        instagramUserEntity.setInstagram_user_summary_dto_list(collect);
        instagramUserRepo.save(instagramUserEntity);
        System.out.println("---saveUserAndFollowersToDb--- SAVED");
    }

    public List<InstagramUserSummary> getFollowers() {
        return this.followers;
    }

     void saveUserToDb(){}
     void saveFollowerToDb(){}
     InstagramUser getUser(String username) throws IOException {
        if (!instagramUserRepo.existsByUsername(username)) {
            return getUserFromInternet(username);
        } else {
            InstagramUserEntity userFromDb = getUserFromDb(username);
            return InstagramUserConverter.convertBack(userFromDb);
        }
    }

    List<InstagramUserSummary> getFollower(String username) throws IOException, InterruptedException {
        if (!instagramUserRepo.existsByUsername(username)) {
            InstagramUser user = getUser(username);
            return getUserFollowerFromInternet(user);
        } else {
            InstagramUserEntity userFromDb = getUserFromDb(username);
            List<InstagramUserSummaryDto> instagram_user_summary_dto_list = userFromDb.getInstagram_user_summary_dto_list();
            return instagram_user_summary_dto_list.stream()
                    .map(InstagramUserSummaryToDtoConverter::convertBack)
                    .collect(Collectors.toList());
        }
    }

    List<InstagramUserSummaryDto> getUserFollowerFromDb(InstagramUserEntity instagramUserEntity) {
        return instagramUserEntity.getInstagram_user_summary_dto_list();
    }

    List<InstagramUserSummary> getUserFollowerFromInternet(InstagramUser instagramUser) throws InterruptedException {
        Instagram4j instagram = loginService.getInstagram4j();
        List<InstagramUserSummary> followersList = new ArrayList<>();
        InstagramGetUserFollowersResult followersResult = null;
        String maxId = null;
        while (followersResult == null || maxId != null) {
            try {
                followersResult = instagram.sendRequest(new
                        InstagramGetUserFollowersRequest(instagramUser.pk, maxId));
                if (followersResult != null) {
                    if (followersResult.getStatus().equals("ok")) {
                        maxId = followersResult.getNext_max_id();
                        followersList.addAll(followersResult.getUsers());
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                Thread.sleep(5000);
            }
        }
        return followersList;
    }

    InstagramUserEntity getUserFromDb(String username) {
        InstagramUserEntity user = instagramUserRepo.findByUsername(username);
        return user;
    }

    InstagramUser getUserFromInternet(String username) throws IOException {
        InstagramSearchUsernameResult instagramSearchUsernameResult = loginService.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
        InstagramUser user = instagramSearchUsernameResult.getUser();
        return user;
    }

    public void follow(Long userpk) {
        RandomDelayUtill.delay(3000);
        InstagramFollowRequest instagramFollowRequest = new InstagramFollowRequest(userpk);
        try {
            loginService.getInstagram4j().sendRequest(instagramFollowRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void follow(List<InstagramUserSummary> instagramUserSummaryList) {
        instagramUserSummaryList.stream()
                .map(InstagramUserSummary::getPk)
                .forEach(this::follow);
    }

    public void unfollow(Long userpk) {
        RandomDelayUtill.delay(13000);
        InstagramUnfollowRequest instagramUnfollowRequest = new InstagramUnfollowRequest(userpk);
        try {
            loginService.getInstagram4j().sendRequest(instagramUnfollowRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unfollow(List<InstagramUserSummary> instagramUserSummaryList) {
        instagramUserSummaryList.stream()
                .map(InstagramUserSummary::getPk)
                .forEach(this::unfollow);
    }

    String saveToDb() {
        InstagramUserSummary instagramUserSummary = followers.get(0);

        return "ok";
    }

}
