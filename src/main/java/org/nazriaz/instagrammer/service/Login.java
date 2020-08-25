package org.nazriaz.instagrammer.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetChallengeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramResetChallengeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSelectVerifyMethodRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSendSecurityCodeRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetChallengeResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSelectVerifyMethodResult;
import org.nazriaz.instagrammer.Util.SessionConverter;
import org.nazriaz.instagrammer.dao.InstagramSession;
import org.nazriaz.instagrammer.repository.InstagramSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
@Service
@NoArgsConstructor
@Data
public class Login {
    @Autowired
    InstagramSessionRepo instagramSessionRepo;
    Instagram4j instagram4j;
    public String login() throws IOException {
        instagram4j = Instagram4j.builder()
                .username("СУДА_ЛОГИН")
                .password("ПАРОЛЬ")
                .build();
        instagram4j.setup();

        InstagramLoginResult instagramLoginResult = instagram4j.login();

        if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {
            System.out.println("login success");
        } else {
            if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
                // Challenge required

                // Get challenge URL
                String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);

                // Reset challenge
                String resetChallengeUrl = challengeUrl.replace("challenge", "challenge/reset");
                InstagramGetChallengeResult getChallengeResult = instagram4j
                        .sendRequest(new InstagramResetChallengeRequest(resetChallengeUrl));

                // If action is close
                if (Objects.equals(getChallengeResult.getAction(), "close")) {
                    // Get challenge
                    getChallengeResult = instagram4j
                            .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
                }

                if (Objects.equals(getChallengeResult.getStep_name(), "select_verify_method")) {

                    // Get security code
                    InstagramSelectVerifyMethodResult postChallengeResult = instagram4j
                            .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                    getChallengeResult.getStep_data().getChoice()));

                    System.out.println("input security code");
                    String securityCode = null;
                    try (Scanner scanner = new Scanner(System.in)) {
                        securityCode = scanner.nextLine();
                    }

                    // Send security code
                    InstagramLoginResult securityCodeInstagramLoginResult = instagram4j
                            .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));

                    if (Objects.equals(securityCodeInstagramLoginResult.getStatus(), "ok")) {
                        System.out.println("login success");
                    } else {
                        System.out.println("login failed");
                    }
                }
            }
        }
        return "ok";
    }
    public void getSessionFromDb(){
        InstagramSession one = instagramSessionRepo.getOne(0);
        Instagram4j session = SessionConverter.convertToInstagram4j(one);
        setInstagram4j(session);
        System.out.println("Session successfully loaded");
    }
    public void saveCurrentSession(){
        InstagramSession instagramSession = SessionConverter.convertToInstagramSession(instagram4j);
        instagramSessionRepo.save(instagramSession);
        System.out.println("Success to save CurrentSession");
    }

}
