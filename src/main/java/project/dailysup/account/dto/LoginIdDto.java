package project.dailysup.account.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class LoginIdDto {

    private String loginId;

    public LoginIdDto(String loginId) {
        this.loginId = loginId;
    }

    public static LoginIdDto of(String loginId){
        return new LoginIdDto(loginId);
    }
}
