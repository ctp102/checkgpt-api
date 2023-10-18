package io.hexbit.core.user.domain;

import io.hexbit.core.common.domain.BaseTimeEntity;
import io.hexbit.core.user.enums.UserDeviceTypes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_SESSION_DEVICE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSessionDevice extends BaseTimeEntity {

    @Id
    @Column(name = "DEVICE_UUID")
    private String deviceUUID;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "DEVICE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserDeviceTypes deviceType;

    @Column(name = "DEVICE_COUNTRY_CD")
    private String deviceCountryCd; // Country Code

    @Column(name = "DEVICE_LANG_CD")
    private String deviceLangCd; // Language Code

    @Column(name = "DEVICE_TIMEZONE")
    private String deviceTimezone;

    @Column(name = "DEVICE_LAST_ACCESS_IP", nullable = false)
    private String deviceLastAccessIp;

    @Column(name = "DEVICE_APP_VERSION")
    private String deviceAppVersion;

    private String userAgent;

    @Builder
    public UserSessionDevice(String deviceUUID, String deviceToken, User user, UserDeviceTypes deviceType, String deviceCountryCd, String deviceLangCd, String deviceTimezone, String deviceLastAccessIp, String deviceAppVersion, String userAgent) {
        this.deviceUUID = deviceUUID;
        this.deviceToken = deviceToken;
        this.user = user;
        this.deviceType = deviceType;
        this.deviceCountryCd = deviceCountryCd;
        this.deviceLangCd = deviceLangCd;
        this.deviceTimezone = deviceTimezone;
        this.deviceLastAccessIp = deviceLastAccessIp;
        this.deviceAppVersion = deviceAppVersion;
        this.userAgent = userAgent;
    }

    public void addUser(User user) {
        this.user = user;
    }

}
