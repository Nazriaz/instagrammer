package org.nazriaz.instagrammer;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUnfollowRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.service.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
    List<InstagramUserSummary> followers = new ArrayList<>();
    @Autowired
    Login login;
    @GetMapping("/login")
    String login() throws IOException {
        return login.login();
    }
    @GetMapping("/load")
    String load(){
        login.getSessionFromDb();
        return "loaded";
    }
    @GetMapping("/save")
    String save(){
        login.saveCurrentSession();
        return "saved";
    }
    @GetMapping("/getuser")
    String getUser(@RequestParam String username) throws IOException {
        InstagramSearchUsernameResult instagramSearchUsernameResult = login.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
        StringBuilder stringBuilder = new StringBuilder()
                .append("full_name").append(instagramSearchUsernameResult.getUser().full_name).append("\n")
                .append("biography").append(instagramSearchUsernameResult.getUser().biography).append("\n")
                .append("follower_count").append(instagramSearchUsernameResult.getUser().follower_count).append("\n")
                .append("following_count").append(instagramSearchUsernameResult.getUser().following_count).append("\n");
        return stringBuilder.toString();
    }
    @GetMapping("/getuserfollowers")
    List getUserFollowers(@RequestParam String username) throws IOException {
        InstagramSearchUsernameResult userResult = login.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
        InstagramGetUserFollowersResult followersResult = login.getInstagram4j().sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));

        List<InstagramUserSummary> users = followersResult.getUsers();
        for (InstagramUserSummary user : users) {
            System.out.println("User " + user.getUsername() + " follows!");
        }
        return users;
    }
    @GetMapping("/get")
    List get(@RequestParam String username) throws IOException, InterruptedException {
        InstagramSearchUsernameResult instagramSearchUsernameResult = login.getInstagram4j().sendRequest(new InstagramSearchUsernameRequest(username));
        Instagram4j instagram = login.getInstagram4j();
        InstagramGetUserFollowersResult followersResult = null;
        String maxId = null;

        while (followersResult == null || maxId != null) {
            try {
                followersResult =  instagram.sendRequest(new
                        InstagramGetUserFollowersRequest(instagramSearchUsernameResult.getUser().pk,maxId));
                if (followersResult != null) {
                    if (followersResult.getStatus().equals("ok")) {
                        maxId = followersResult.getNext_max_id();
                        followers.addAll(followersResult.getUsers());
                    }else{
                        break;
                    }
                }
            } catch (IOException e) {
                Thread.sleep(5000);
            }
        }
        System.out.println(followers);
        return followers;
    }
    @GetMapping("/follow")
    String follow(){
        Instagram4j instagram = login.getInstagram4j();
        followers.forEach(follower->{
            try {
                Thread.sleep(8000+Math.round(Math.random()*20000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InstagramFollowRequest instagramFollowRequest = new InstagramFollowRequest(follower.pk);
            try {
                instagram.sendRequest(instagramFollowRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "followed";
    }
    @GetMapping("/unfollow")
    String unfollow(){
        Instagram4j instagram = login.getInstagram4j();
        followers.forEach(follower->{
            try {
                Thread.sleep(1000+Math.round(Math.random()*4000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InstagramUnfollowRequest instagramFollowRequest = new InstagramUnfollowRequest(follower.pk);
            try {
                instagram.sendRequest(instagramFollowRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "unfollowed";
    }

}
