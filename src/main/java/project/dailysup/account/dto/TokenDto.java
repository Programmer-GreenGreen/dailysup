package project.dailysup.account.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenDto {

    private final String jwtToken;

}
