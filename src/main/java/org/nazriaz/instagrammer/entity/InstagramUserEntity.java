package org.nazriaz.instagrammer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.brunocvcunha.instagram4j.requests.payload.InstagramProfilePic;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Data
@Entity(name = "InstagramUser")
@AllArgsConstructor
@NoArgsConstructor
public class InstagramUserEntity {
    public LocalDate date;
    public boolean is_private;
    public boolean is_verified;
    public String username;
    public boolean has_chaining;
    public boolean is_business;
    public int media_count;
    public String profile_pic_id;
    @Column(length=1000)
    public String external_url;
    public String full_name;
    public boolean has_biography_translation;
    public boolean has_anonymous_profile_picture;
    public boolean is_favorite;
    public String public_phone_country_code;
    public String public_phone_number;
    public String public_email;
    @Id
    public long pk;
    public int geo_media_count;
    public int usertags_count;
    @Column(length=1000)
    public String profile_pic_url;
    public String address_street;
    public String city_name;
    public String zip;
    public String direct_messaging;
    public String business_contact_method;
    public String biography;
    public int follower_count;
//    @Lob
//    public List<InstagramProfilePic> hd_profile_pic_versions;
//    @Lob
//    public InstagramProfilePic hd_profile_pic_url_info;
    @Column(length=1000)
    public String external_lynx_url;
    public int following_count;
    public float latitude;
    public float longitude;
    public String category;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "User_Follower",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "follower_id") }
    )
    public List<InstagramUserSummaryDto> instagram_user_summary_dto_list;
}
