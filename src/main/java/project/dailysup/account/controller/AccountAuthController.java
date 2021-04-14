package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dailysup.account.service.AccountAuthService;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/auth")
public class AccountAuthController {

    private final AccountAuthService accountAuthService;



    @PostMapping("/log-in")
    public ResponseEntity<?> createToken(@RequestBody LogInRequestDto dto){
        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        String jwtToken = accountAuthService.authAccount(loginId, password);

        log.info(LogFactory.create(LogCode.CREATE_TOKEN,dto.getLoginId()));
        return ResponseEntity.ok(new TokenResponseDto(jwtToken));
    }


    /**
     * DTO
     */

    @Getter
    @AllArgsConstructor
    public static class TokenResponseDto {

        private String jwtToken;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LogInRequestDto {

        private String loginId;
        private String password;
    }
}
