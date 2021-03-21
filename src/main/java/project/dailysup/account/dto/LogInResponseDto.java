package project.dailysup.account.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LogInResponseDto {

    private String loginId;

    private String jwtToken;

    private String nickname;
}
