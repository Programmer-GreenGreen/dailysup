package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.dto.LoginIdDto;
import project.dailysup.account.service.AccountForgetService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/forget")
public class AccountForgetController {

    private final AccountForgetService accountForgetService;

    @GetMapping("/password")
    public ResponseEntity<?> validateToken(@RequestBody ResetToken dto){
        boolean isSuccess = accountForgetService.validateResetToken(dto.getLoginId(), dto.getToken());
        return isSuccess ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/password")
    public ResponseEntity<?> forgetPassword(@RequestBody LoginIdDto dto){
        accountForgetService.sendTokenByEmail(dto.getLoginId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> setPassword(@RequestBody ForgetToken dto){
        accountForgetService.setPassword(dto.getLoginId(), dto.getPassword(), dto.getToken());
        return ResponseEntity.ok().build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class ResetToken {
        private String loginId;
        private String token;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ForgetToken {
        private String loginId;
        private String password;
        private String token;
    }
}
