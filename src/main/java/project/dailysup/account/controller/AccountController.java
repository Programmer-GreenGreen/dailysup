package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.domain.Account;
import project.dailysup.account.dto.*;
import project.dailysup.account.service.AccountQueryService;
import project.dailysup.account.service.AccountRegisterService;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRegisterService accountRegisterService;
    private final AccountQueryService accountQueryService;

    @GetMapping
    public ResponseEntity<?> getCurrentAccount() {

        Account findAccount =  accountQueryService.findCurrentAccount();

        log.info(LogFactory.create(LogCode.CURR_ACC, findAccount.getLoginId()));

        RetrieveAccountDto dto = RetrieveAccountDto.builder()
                .loginId(findAccount.getLoginId())
                .nickname(findAccount.getNickname())
                .email(findAccount.getEmail())
                .profilePictureUrl(findAccount.getProfilePictureUrl())
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto dto) {

        log.info(LogFactory.create(LogCode.SIGN_UP,dto.getLoginId()));
        SignUpResponseDto signUpResponseDto
                = accountRegisterService.singUp(dto.getLoginId(), dto.getPassword(), dto.getNickname());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto dto) {


        log.info(LogFactory.create(LogCode.WITHDRAW, dto.getLoginId()));
        LoginIdDto deletedId = accountRegisterService.withdraw(dto.getLoginId(),dto.getPassword());

        return ResponseEntity.ok(deletedId);
    }



    /**
     * DTO
     */

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class WithdrawDto {

        private String loginId;
        private String password;
    }

    @Getter @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED) // used for serialized. (proxy)
    public static class SignUpRequestDto {

        @Length(min = 5, max = 30)
        private String loginId;

        @Length(min = 5)
        private String password;

        @Length(min = 5, max = 30)
        private String nickname;

    }
}
