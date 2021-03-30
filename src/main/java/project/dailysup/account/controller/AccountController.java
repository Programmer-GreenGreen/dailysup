package project.dailysup.account.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.dto.*;
import project.dailysup.account.service.AccountService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getCurrentUser() {
        RetrieveAccountDto dto = accountService.findCurrentAccountDto();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto dto) {

        SignUpResponseDto signUpResponseDto = accountService.singUp(dto);
        return ResponseEntity.ok(signUpResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto) {
        LoginIdDto deletedId = accountService.withdraw(withdrawDto);

        return ResponseEntity.ok(deletedId);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class WithdrawDto {

        private String loginId;
        private String password;
    }
}
