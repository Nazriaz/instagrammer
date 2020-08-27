package org.nazriaz.instagrammer.service;

import org.brunocvcunha.instagram4j.requests.InstagramLikeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUserFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.nazriaz.instagrammer.Util.RandomDelayUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {
    @Autowired
    LoginService loginService;
    @Autowired
    InstagramUserService instagramUserService;

    public List<InstagramFeedItem> getLastFeed(String username) throws IOException {
        InstagramUser instagramUser = instagramUserService.getUserFromUserName(username);
        InstagramFeedResult userFeed = loginService.getInstagram4j().sendRequest(new InstagramUserFeedRequest(instagramUser.pk));
        List<InstagramFeedItem> items = userFeed.getItems();

        return items;
    }

    public List<InstagramFeedItem> getAllFeed(String username) throws IOException, InterruptedException {
        InstagramUser instagramUser = instagramUserService.getUserFromUserName(username);
        InstagramFeedResult instagramFeedResult = null;
        String maxId = null;
        List<InstagramFeedItem> feeds = new ArrayList<>();
        while (instagramFeedResult == null || maxId != null) {
            try {
                instagramFeedResult = loginService.getInstagram4j().sendRequest(new
                        InstagramUserFeedRequest(instagramUser.pk, maxId, 0, 0));
                if (instagramFeedResult != null) {
                    if (instagramFeedResult.getStatus().equals("ok")) {
                        maxId = instagramFeedResult.getNext_max_id();
                        feeds.addAll(instagramFeedResult.getItems());
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {

                Thread.sleep(5000);
                break;
            }
        }
        return feeds;
    }

    public void postLike(InstagramFeedItem instagramFeedItem) throws IOException {
        loginService.getInstagram4j().sendRequest(new InstagramLikeRequest(instagramFeedItem.getPk()));

        RandomDelayUtill.delay(8000);
    }

    public String postLike(List<InstagramFeedItem> instagramFeedItemList) throws IOException {
        for (InstagramFeedItem instagramFeedItem : instagramFeedItemList) {
            if (!instagramFeedItem.has_liked) {
                postLike(instagramFeedItem);
                System.out.print("instagramFeedItem.id_" + instagramFeedItem.id + " has_liked=" + instagramFeedItem.has_liked + "now is Liked");
            } else {
                System.out.println("instagramFeedItem.id_" + instagramFeedItem.id + " already was Liked");
            }
        }
        return "ok";
    }


}
