package project.dailysup.account.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dailysup.account.dto.LogInRequestDto;
import project.dailysup.account.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/auth")
public class AccountAuthController {

    private final AccountService accountService;


    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@RequestBody LogInRequestDto dto){
        String loginId = dto.getLoginId();
        String password = dto.getPassword();

        String jwtToken = accountService.logIn(loginId, password);

        return ResponseEntity.ok(new TokenResponseDto(jwtToken));
    }


    @Getter
    @AllArgsConstructor
    public static class TokenResponseDto {

        private String jwtToken;

    }
}
