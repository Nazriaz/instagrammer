package org.nazriaz.instagrammer.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class InstagramSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String username;
    String password;
    long userId;
    String uuid;
    CookieStore cookieStore;
    HttpHost proxy;
    CredentialsProvider credentialsProvider;

    public InstagramSession(String username, String password, long userId, String uuid, CookieStore cookieStore, HttpHost proxy, CredentialsProvider credentialsProvider) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.uuid = uuid;
        this.cookieStore = cookieStore;
        this.proxy = proxy;
        this.credentialsProvider = credentialsProvider;
    }
}
