package project.dailysup.account.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class SignUpResponseDto {

    private final String loginId;

    private final String nickname;

    @Builder
    public SignUpResponseDto(String loginId, String nickname) {
        this.loginId = loginId;
        this.nickname = nickname;
    }
}
