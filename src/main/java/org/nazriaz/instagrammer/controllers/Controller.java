package org.nazriaz.instagrammer.controllers;

import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.service.FeedService;
import org.nazriaz.instagrammer.service.InstagramUserService;
import org.nazriaz.instagrammer.service.InstagramUserSummaryService;
import org.nazriaz.instagrammer.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
    List<InstagramUserSummary> followers = new ArrayList<>();
    @Autowired
    FeedService feedService;
    @Autowired
    LoginService loginService;
    @Autowired
    InstagramUserService instagramUserService;
    @Autowired
    InstagramUserSummaryService instagramUserSummaryService;

    @GetMapping("/login")
    String login() throws IOException {
        return loginService.login();
    }

    @GetMapping("/load/{id}")
    String load(@PathVariable String id) throws IOException {
        loginService.getSessionFromDb(id);
        return "loaded";
    }

    @GetMapping("/save")
    String save() {
        loginService.saveCurrentSession();
        return "saved";
    }
    @GetMapping("/savefollowers")
    String savefollowers(){
        List<InstagramUserSummary> followers = instagramUserService.getFollowers();
        instagramUserSummaryService.saveToDb(followers);
        return "savefollowers done";
    }

    @GetMapping("/getuser")
    String getUser(@RequestParam String username) throws IOException {
        InstagramUser instagramUser = instagramUserService.getUserFromUserName(username);
        StringBuilder stringBuilder = new StringBuilder()
                .append("full_name").append(instagramUser.full_name).append("\n")
                .append("biography").append(instagramUser.biography).append("\n")
                .append("follower_count").append(instagramUser.follower_count).append("\n")
                .append("following_count").append(instagramUser.following_count).append("\n");
        return stringBuilder.toString();
    }

    @GetMapping("/getfeed")
    List getFeed(@RequestParam String username) throws IOException {
        List<InstagramFeedItem> feed = feedService.getLastFeed(username);
        for (InstagramFeedItem feedResult : feed) {
            System.out.println("Post ID: " + feedResult.getPk());
        }
        return feed;
    }

    @GetMapping("/getuserfollowers")
    List getUserFollowers(@RequestParam String username) throws IOException {
        InstagramUser instagramUser = instagramUserService.getUserFromUserName(username);
        InstagramGetUserFollowersResult followersResult = loginService.getInstagram4j().sendRequest(new InstagramGetUserFollowersRequest(instagramUser.getPk()));

        List<InstagramUserSummary> users = followersResult.getUsers();
        for (InstagramUserSummary user : users) {
            System.out.println("User " + user.getUsername() + " follows!");
        }
        return users;
    }

    @GetMapping("/get")
    List get(@RequestParam String username) throws IOException, InterruptedException {
        instagramUserService.searchUserFollowers(username);
        return instagramUserService.getFollowers();
    }

    @GetMapping("/getall")
    List getAll(@RequestParam String username) throws IOException, InterruptedException {
        return feedService.getAllFeed(username);
    }

    @GetMapping("/likeall")
    String likeAll(@RequestParam String username) throws IOException, InterruptedException {
        List<InstagramFeedItem> allFeed = feedService.getAllFeed(username);
        feedService.postLike(allFeed);
        return "ok";
    }

    @GetMapping("/follow")
    String follow() {
        List<InstagramUserSummary> followers = instagramUserService.getFollowers();
        instagramUserService.follow(followers);
        return "followed";
    }

    @GetMapping("/unfollow")
    String unfollow() {
        List<InstagramUserSummary> followers = instagramUserService.getFollowers();
        instagramUserService.unfollow(followers);
        return "unfollowed";
    }

}
