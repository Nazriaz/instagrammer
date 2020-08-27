package org.nazriaz.instagrammer.Util;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.nazriaz.instagrammer.entity.InstagramSession;

public class SessionConverter {
    public static Instagram4j convertToInstagram4j(InstagramSession instagramSession){
        return new Instagram4j(instagramSession.getUsername(),
                instagramSession.getPassword(),
                instagramSession.getUserId(),
                instagramSession.getUuid(),
                instagramSession.getCookieStore(),
                instagramSession.getProxy(),
                null);
    }
    public static InstagramSession convertToInstagramSession(Instagram4j instagram4j){
        return new InstagramSession(instagram4j.getUsername(),
                instagram4j.getPassword(),
                instagram4j.getUserId(),
                instagram4j.getUuid(),
                instagram4j.getCookieStore(),
                instagram4j.getProxy(),
                instagram4j.getCredentialsProvider());
    }
}
