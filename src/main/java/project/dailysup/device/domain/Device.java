package project.dailysup.device.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.dailysup.account.domain.Account;
import project.dailysup.common.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseEntity {

    @Id
    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;


    public Device(String fcmToken, Account account) {
        this.fcmToken = fcmToken;
        setUpAccount(account);
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setUpAccount(Account account){
        account.getDeviceList().add(this);
        this.account = account;
    }


}
