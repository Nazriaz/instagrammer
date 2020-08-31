package org.nazriaz.instagrammer.Util;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.nazriaz.instagrammer.entity.InstagramUserSummaryDto;

public class InstagramUserSummaryToDtoConverter {
    public static InstagramUserSummaryDto convert(InstagramUserSummary instagramUserSummary) {
        return new InstagramUserSummaryDto(
                instagramUserSummary.is_verified,
                instagramUserSummary.profile_pic_id,
                instagramUserSummary.is_favorite,
                instagramUserSummary.is_private,
                instagramUserSummary.username,
                instagramUserSummary.pk,
                instagramUserSummary.profile_pic_url,
                instagramUserSummary.has_anonymous_profile_picture,
                instagramUserSummary.full_name,null);
    }

    public static InstagramUserSummary convertBack(InstagramUserSummaryDto instagramUserSummaryDto) {
        InstagramUserSummary instagramUserSummary = new InstagramUserSummary();
        instagramUserSummary.set_verified(instagramUserSummaryDto.is_verified);
        instagramUserSummary.setProfile_pic_id(instagramUserSummaryDto.profile_pic_id);
        instagramUserSummary.set_favorite(instagramUserSummaryDto.is_favorite);
        instagramUserSummary.set_private(instagramUserSummaryDto.is_private);
        instagramUserSummary.setUsername(instagramUserSummaryDto.username);
        instagramUserSummary.setPk(instagramUserSummaryDto.pk);
        instagramUserSummary.setProfile_pic_url(instagramUserSummaryDto.profile_pic_url);
        instagramUserSummary.setHas_anonymous_profile_picture(instagramUserSummaryDto.has_anonymous_profile_picture);
        instagramUserSummary.setFull_name(instagramUserSummaryDto.full_name);
        return instagramUserSummary;
    }
}
