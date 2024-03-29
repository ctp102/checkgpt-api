package io.hexbit.core.oauth2.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoOAuth2User {

    private long id; // 회원번호
    private java.sql.Timestamp connectedAt; // 서비스에 완료된 시각, UTC
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private Profile profile;
        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;
        private boolean hasAgeRange;
        private boolean ageRangeNeedsAgreement;
        private String ageRange; // ex) 20~29
        private boolean hasBirthday;
        private boolean birthdayNeedsAgreement;
        private String birthday; // ex) 1002
        private String birthdayType; // ex) SOLAR
        private boolean hasGender;
        private boolean genderNeedsAgreement;
        private String gender; // ex) male

        @Data
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Profile {
            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private boolean isDefaultImage;
        }
    }

}
