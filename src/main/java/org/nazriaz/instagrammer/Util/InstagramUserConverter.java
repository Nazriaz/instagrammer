package org.nazriaz.instagrammer.Util;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.nazriaz.instagrammer.entity.InstagramUserEntity;

import java.time.LocalDate;

public class InstagramUserConverter {
    public static InstagramUserEntity convert(InstagramUser instagramUser) {
        InstagramUserEntity instagramUserEntity = new InstagramUserEntity();
        instagramUserEntity.setDate(LocalDate.now());
        instagramUserEntity.set_private(instagramUser.is_private);
        instagramUserEntity.set_verified(instagramUser.is_verified);
        instagramUserEntity.setUsername(instagramUser.username);
        instagramUserEntity.setHas_chaining(instagramUser.has_chaining);
        instagramUserEntity.set_business(instagramUser.is_business);
        instagramUserEntity.setMedia_count(instagramUser.media_count);
        instagramUserEntity.setProfile_pic_id(instagramUser.profile_pic_id);
        instagramUserEntity.setExternal_url(instagramUser.external_url);
        instagramUserEntity.setFull_name(instagramUser.full_name);
        instagramUserEntity.setHas_biography_translation(instagramUser.has_biography_translation);
        instagramUserEntity.setHas_anonymous_profile_picture(instagramUser.has_anonymous_profile_picture);
        instagramUserEntity.set_favorite(instagramUser.is_favorite);
        instagramUserEntity.setPublic_phone_country_code(instagramUser.public_phone_country_code);
        instagramUserEntity.setPublic_phone_number(instagramUser.public_phone_number);
        instagramUserEntity.setPublic_email(instagramUser.public_email);
        instagramUserEntity.setPk(instagramUser.pk);
        instagramUserEntity.setGeo_media_count(instagramUser.geo_media_count);
        instagramUserEntity.setUsertags_count(instagramUser.usertags_count);
        instagramUserEntity.setProfile_pic_url(instagramUser.profile_pic_url);
        instagramUserEntity.setAddress_street(instagramUser.address_street);
        instagramUserEntity.setCity_name(instagramUser.city_name);
        instagramUserEntity.setZip(instagramUser.zip);
        instagramUserEntity.setDirect_messaging(instagramUser.direct_messaging);
        instagramUserEntity.setBusiness_contact_method(instagramUser.business_contact_method);
        instagramUserEntity.setBiography(instagramUser.biography);
        instagramUserEntity.setFollower_count(instagramUser.follower_count);
//        instagramUserEntity.setHd_profile_pic_versions(instagramUser.hd_profile_pic_versions); //List<InstagramProfilePic>
//        instagramUserEntity.setHd_profile_pic_url_info(instagramUser.hd_profile_pic_url_info); //InstagramProfilePic
        instagramUserEntity.setExternal_lynx_url(instagramUser.external_lynx_url);
        instagramUserEntity.setFollowing_count(instagramUser.following_count);
        instagramUserEntity.setLatitude(instagramUser.latitude); //float
        instagramUserEntity.setLongitude(instagramUser.longitude); //float
        instagramUserEntity.setCategory(instagramUser.category);
        return instagramUserEntity;
    }

    public static InstagramUser convertBack(InstagramUserEntity instagramUserEntity) {
        InstagramUser instagramUser = new InstagramUser();
        instagramUser.set_private(instagramUserEntity.is_private);
        instagramUser.set_verified(instagramUserEntity.is_verified);
        instagramUser.setUsername(instagramUserEntity.username);
        instagramUser.setHas_chaining(instagramUserEntity.has_chaining);
        instagramUser.set_business(instagramUserEntity.is_business);
        instagramUser.setMedia_count(instagramUserEntity.media_count);
        instagramUser.setProfile_pic_id(instagramUserEntity.profile_pic_id);
        instagramUser.setExternal_url(instagramUserEntity.external_url);
        instagramUser.setFull_name(instagramUserEntity.full_name);
        instagramUser.setHas_biography_translation(instagramUserEntity.has_biography_translation);
        instagramUser.setHas_anonymous_profile_picture(instagramUserEntity.has_anonymous_profile_picture);
        instagramUser.set_favorite(instagramUserEntity.is_favorite);
        instagramUser.setPublic_phone_country_code(instagramUserEntity.public_phone_country_code);
        instagramUser.setPublic_phone_number(instagramUserEntity.public_phone_number);
        instagramUser.setPublic_email(instagramUserEntity.public_email);
        instagramUser.setPk(instagramUserEntity.pk);
        instagramUser.setGeo_media_count(instagramUserEntity.geo_media_count);
        instagramUser.setUsertags_count(instagramUserEntity.usertags_count);
        instagramUser.setProfile_pic_url(instagramUserEntity.profile_pic_url);
        instagramUser.setAddress_street(instagramUserEntity.address_street);
        instagramUser.setCity_name(instagramUserEntity.city_name);
        instagramUser.setZip(instagramUserEntity.zip);
        instagramUser.setDirect_messaging(instagramUserEntity.direct_messaging);
        instagramUser.setBusiness_contact_method(instagramUserEntity.business_contact_method);
        instagramUser.setBiography(instagramUserEntity.biography);
        instagramUser.setFollower_count(instagramUserEntity.follower_count);
//        instagramUser.setHd_profile_pic_versions(instagramUserEntity.hd_profile_pic_versions); //List<InstagramProfilePic>
//        instagramUser.setHd_profile_pic_url_info(instagramUserEntity.hd_profile_pic_url_info); //InstagramProfilePic
        instagramUser.setExternal_lynx_url(instagramUserEntity.external_lynx_url);
        instagramUser.setFollowing_count(instagramUserEntity.following_count);
        instagramUser.setLatitude(instagramUserEntity.latitude); //float
        instagramUser.setLongitude(instagramUserEntity.longitude); //float
        instagramUser.setCategory(instagramUserEntity.category);
        return instagramUser;
    }
}
