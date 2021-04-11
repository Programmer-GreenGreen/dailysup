package project.dailysup.account.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import project.dailysup.account.exception.InvalidPasswordException;
import project.dailysup.account.exception.NotValidWithdrawRequest;
import project.dailysup.common.BaseEntity;
import project.dailysup.device.domain.Device;
import project.dailysup.item.domain.Item;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreType
@Getter @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(columnList = "loginId", name = "idx_acc"))
public class Account extends BaseEntity {



    /**
     *  Unique 하면서 Not Null 한 필드들
     */

    @Id
    @GeneratedValue
    @Column(name = "acccount_id")
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    @Length(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH)
    private String loginId;


    /**
     * Nullable 하기만 한 필드들
     */
    @Column(nullable = false)
    @Length(min = NICKNAME_MIN_LENGTH, max = NICKNAME_MAX_LENGTH)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean isActivated;

    /**
     * Unique 하기만 한 빌드들
     */

    @Column(unique = true)
    private String email;

    /**
     * 제약조건 없는 필드들
     */

    @Embedded
    private ResetToken resetToken; // 이메일 인증시 발송되는 토큰.
    private String profilePictureUrl;

    /**
     *  연관관계 매핑
     */

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> deviceList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> itemList = new ArrayList<>();


    /**
     * Composition 관계의 객체 CRUD 메서드
     * Account가 연관관계의 주인은 아니나, Aggregation root 이다.
     */

    public String addDevice(Device device){
        this.deviceList.add(device);
        return device.getFcmToken();
    }

    public void changeDeviceList(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    public boolean removeDevice(String fcmToken){
        return deviceList.stream()
                .filter(d -> d.getFcmToken().equals(fcmToken))
                .findFirst()
                .filter(deviceList::remove)
                .isPresent();

    }

    public void addItem(Item item){
        this.itemList.add(item);
    }

    public boolean removeItem(Long itemId){
        return itemList.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .filter(itemList::remove)
                .isPresent();

    }


    /**
     * DDD 메서드. 검증 로직이 들어있다.
     */


    /**
     * 비밀번호를 새로 설정한다.
     */
    public void setPassword(PasswordEncoder encoder, String password){
        if(encoder == null || encoder instanceof NoOpPasswordEncoder){
            throw new InvalidPasswordException("비밀번호 변경에 오류가 있습니다.");
        }
        if(!StringUtils.hasText(password)){
            throw new InvalidPasswordException("비밀번호 변경에 오류가 있습니다.");
        }
        this.password = encoder.encode(password);
    }

    /**
     * 비밀번호를 변경한다.
     */

    public void changePassword(PasswordEncoder encoder, String oldPassword, String newPassword){
        boolean isPasswordMatch = encoder.matches(oldPassword, this.password);
        if(!isPasswordMatch){
            throw new InvalidPasswordException("비밀번호 변경에 오류가 있습니다.");
        }
        setPassword(encoder, newPassword);
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changeProfile(String profilePictureUrl){
        this.profilePictureUrl = profilePictureUrl;
    }

    public void changeNickname(String nickname){
        if(!StringUtils.hasText(nickname)){
            throw new IllegalStateException("닉네임 변경에 오류가 있습니다.");
        }
        this.nickname = nickname;
    }

    /**
     *  회원 탈퇴한다. ( DB 에서 삭제가 아니라 활성화 상태를 false로 만든다.)
     */

    public void changeToNotActivated(String loginId, String password, PasswordEncoder passwordEncoder){

        boolean isSameId = this.loginId.equals(loginId);
        boolean isPasswordMatch = passwordEncoder.matches(password,this.password);

        if(!isSameId || !isPasswordMatch){
            throw new NotValidWithdrawRequest();
        }

        this.isActivated = false;
    }

    /**
     * 비밀번호 재설정 토큰
     */

    public ResetToken getResetToken() {
        return resetToken;
    }

    public void setResetCode(String resetCode, LocalDateTime expire) {
        this.resetToken = new ResetToken(resetCode, expire);
    }



    @Builder
    public Account(String loginId, String password, PasswordEncoder passwordEncoder, String email, String nickname, String profilePictureUrl, Role role, Boolean isActivated) {
        this.loginId = loginId;
        this.setPassword(passwordEncoder, password);
        this.email = email;
        this.nickname = nickname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.isActivated = isActivated;
    }


    /**
     * DTO
     */
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

    /**
     * 필드 길이 제약조건
     */

    private static final int ID_MIN_LENGTH = 5;
    private static final int ID_MAX_LENGTH = 30;
    private static final int NICKNAME_MIN_LENGTH = 5;
    private static final int NICKNAME_MAX_LENGTH = 30;
}
