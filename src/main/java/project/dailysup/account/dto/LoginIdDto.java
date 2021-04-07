package project.dailysup.account.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginIdDto {

    private String loginId;

    public LoginIdDto(String loginId) {
        this.loginId = loginId;
    }

    public static LoginIdDto of(String loginId){
        return new LoginIdDto(loginId);
    }
}
