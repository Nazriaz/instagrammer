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
import org.nazriaz.instagrammer.Util.RandomDelayUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstagramUserService {
    List<InstagramUserSummary> followers = new ArrayList<>();
    @Autowired
    LoginService loginService;

    public InstagramUser getUserFromUserName(String username) throws IOException {
        InstagramSearchUsernameResult instagramSearchUsernameResult = loginService.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
        return instagramSearchUsernameResult.getUser();
    }

    public List<InstagramUserSummary> searchUserFollowers(String username) throws IOException, InterruptedException {
        InstagramUser instagramUser = this.getUserFromUserName(username);
        Instagram4j instagram = loginService.getInstagram4j();
        followers = null;
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
        System.out.println(followers);
        return this.followers;
    }

    public List<InstagramUserSummary> getFollowers() {
        return this.followers;
    }

    public void follow(Long userpk) {
        RandomDelayUtill.delay(18000);
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

}
