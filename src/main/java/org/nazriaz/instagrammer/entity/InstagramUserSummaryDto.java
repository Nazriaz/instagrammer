package org.nazriaz.instagrammer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity(name = "UserSummary")
@AllArgsConstructor
@NoArgsConstructor
public class InstagramUserSummaryDto {
    public boolean is_verified;
    public String profile_pic_id;
    public boolean is_favorite;
    public boolean is_private;
    public String username;
    @Id
    public long pk;
    @Column(length=1000)
    public String profile_pic_url;
    public boolean has_anonymous_profile_picture;
    public String full_name;
    @ManyToMany(mappedBy = "instagram_user_summary_dto_list")
    public List<InstagramUserEntity> instagram_user_entity_list;
}
