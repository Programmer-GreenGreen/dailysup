package project.dailysup.account.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.common.BaseEntity;
import project.dailysup.device.domain.Device;
import project.dailysup.item.domain.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    private static final int ID_MAX_LENGTH = 30;

    @Id
    @GeneratedValue
    @Column(name = "acccount_id")
    private Long id;

    @Column(length = ID_MAX_LENGTH, updatable = false, nullable = false, unique = true)
    private String loginId;


    @Column(nullable = false)
    private String password;

    private String email;

    @Column(nullable = false)
    private String nickname;

    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> deviceList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> itemList = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isActivated;


    @Builder
    public Account(String loginId, String password, String email, String nickname, String profilePictureUrl, Role role, Boolean isActivated) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.isActivated = isActivated;
    }


    public void addDevice(Device device){
        this.deviceList.add(device);
    }

    public boolean removeDevice(String fcmToken){
        return deviceList.stream()
                .filter(d -> d.getFcmToken().equals(fcmToken))
                .findFirst()
                .filter(d -> deviceList.remove(d))
                .isPresent();

    }

    public void changeDeviceList(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changeProfile(String profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }
}
