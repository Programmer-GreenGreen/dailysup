package project.dailysup.account.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dailysup.account.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/info")
public class AccountInfoController {

    private final AccountService accountService;


    @GetMapping("/email")
    public ResponseEntity<?> getEmail(){
        String email = accountService.getEmail();
        EmailDto dto = new EmailDto(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email")
    public ResponseEntity<?> changeEmail(@RequestBody EmailDto emailDto){
        accountService.changeEmail(emailDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> deleteEmail(@RequestBody EmailDto emailDto){
        accountService.deleteEmail();
        return ResponseEntity.ok().build();
    }


    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto dto){
        accountService.changePassword(dto);

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
