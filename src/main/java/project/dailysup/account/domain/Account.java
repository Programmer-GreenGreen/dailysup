package project.dailysup.account.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dailysup.account.exception.NotValidWithdrawRequest;
import project.dailysup.common.BaseEntity;
import project.dailysup.common.exception.InternalErrorException;
import project.dailysup.device.domain.Device;
import project.dailysup.item.domain.Item;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreType
@Getter @Entity
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

    @Embedded
    private ResetToken resetToken;

    @Builder
    public Account(String loginId, String password, PasswordEncoder passwordEncoder, String email, String nickname, String profilePictureUrl, Role role, Boolean isActivated) {
        this.loginId = loginId;
        this.changePassword(passwordEncoder, password);
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

    public void changePassword(PasswordEncoder encoder, String password){
        if(encoder == null || encoder instanceof NoOpPasswordEncoder){
            throw new InternalErrorException("비밀번호 변경에 오류가 있습니다.");
        }
        this.password = encoder.encode(password);
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

    public void changeToNotActivated(String loginId, String password, PasswordEncoder passwordEncoder){
        /*
           DDD based Withdraw Validation
         */

        boolean isSameId = this.loginId.equals(loginId);
        boolean isPasswordMatch = passwordEncoder.matches(this.password, password);

        if(!isSameId || !isPasswordMatch){
            throw new NotValidWithdrawRequest();
        }

        this.isActivated = false;
    }

    public ResetToken getResetToken() {
        return resetToken;
    }

    public void setResetCode(String resetCode, LocalDateTime expire) {
        this.resetToken = new ResetToken(resetCode, expire);
    }

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ResetToken {
        private String resetCode;
        private LocalDateTime expire;

        public ResetToken(String resetCode, LocalDateTime expire) {
            this.resetCode = resetCode;
            this.expire = expire;
        }
    }
}
