package project.dailysup.account.dto;

import lombok.Builder;
import lombok.Getter;
import project.dailysup.account.domain.Account;

@Getter
public class RetrieveAccountDto {

    private String loginId;
    private String nickname;
    private String email;
    private String profilePictureUrl;

    @Builder
    public RetrieveAccountDto(String loginId, String nickname, String email, String profilePictureUrl) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    public RetrieveAccountDto(Account account){
        this.loginId = account.getLoginId();
        this.nickname = account.getNickname();
        this.email = account.getEmail();
        this.profilePictureUrl = account.getProfilePictureUrl();
    }
}
