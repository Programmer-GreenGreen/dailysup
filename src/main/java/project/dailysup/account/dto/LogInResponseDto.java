package project.dailysup.account.dto;

import lombok.Getter;

@Getter
public class LogInResponseDto {

    private String loginId;

    private String jwtToken;

    private String nickname;
}
