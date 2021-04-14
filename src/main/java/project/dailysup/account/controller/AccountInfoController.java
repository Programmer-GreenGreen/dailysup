package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.service.AccountInfoService;
import project.dailysup.account.service.AccountQueryService;
import project.dailysup.account.service.AccountRegisterService;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/info")
public class AccountInfoController {

    /**
     *  Logging 은 서비스에서 한다.
     */

    private final AccountQueryService accountQueryService;
    private final AccountInfoService accountInfoService;


    @GetMapping("/email")
    public ResponseEntity<?> getEmail(){
        String email = accountQueryService.getEmail();
        EmailDto dto = new EmailDto(email);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/email")
    public ResponseEntity<?> changeEmail(@RequestBody EmailDto emailDto){
        accountInfoService.changeEmail(emailDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> deleteEmail(@RequestBody EmailDto emailDto){
        accountInfoService.deleteEmail();
        return ResponseEntity.ok().build();
    }


    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto dto){
        accountInfoService.changePassword(dto.getOldPassword(), dto.getNewPassword());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname")
    public ResponseEntity<?> getNickname(){
        String nickname = accountQueryService.getNickname();
        return ResponseEntity.ok(new NicknameDto(nickname));
    }

    @PostMapping("/nickname")
    public ResponseEntity<?> changeNickname(@RequestBody NicknameDto dto){
        accountInfoService.changeNickname(dto.getNickname());
        return ResponseEntity.ok().build();
    }


    /**
     * DTO
     */



    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class EmailDto {
        private String email;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class NicknameDto{
        private String nickname;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PasswordDto {
        private String oldPassword;
        private String newPassword;
    }
}
