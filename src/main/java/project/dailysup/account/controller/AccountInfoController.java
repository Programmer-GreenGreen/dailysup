package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.service.AccountInfoService;
import project.dailysup.account.service.AccountQueryService;
import project.dailysup.account.service.AccountRegisterService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/info")
public class AccountInfoController {

    private final AccountQueryService accountQueryService;
    private final AccountInfoService accountInfoService;


    @GetMapping("/email")
    public ResponseEntity<?> getEmail(){
        String email = accountQueryService.getEmail();
        EmailDto dto = new EmailDto(email);
        return ResponseEntity.ok().build();
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
        accountInfoService.changePassword(dto.getPassword());

        return ResponseEntity.ok().build();
    }





    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class EmailDto {
        private String email;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PasswordDto {

        private String password;
    }
}
