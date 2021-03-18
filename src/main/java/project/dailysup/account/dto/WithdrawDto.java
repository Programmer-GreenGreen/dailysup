package project.dailysup.account.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WithdrawDto {

    private String loginId;
    private String password;
}
