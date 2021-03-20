package project.dailysup.account.controller;

import com.google.firebase.database.annotations.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.dto.*;
import project.dailysup.account.service.AccountService;
import project.dailysup.util.SecurityUtils;

import javax.validation.Valid;
import java.io.File;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(){
        RetrieveAccountDto dto = accountService.findCurrentAccountDto();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto dto) {

        SignUpResponseDto signUpResponseDto = accountService.singUp(dto);
        return ResponseEntity.ok(signUpResponseDto);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@RequestBody LogInRequestDto logInRequestDto){
        TokenDto tokenDto = accountService.logIn(logInRequestDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto){
        LoginIdDto deletedId  = accountService.withdraw(withdrawDto);

        return ResponseEntity.ok(deletedId);
    }

    @PostMapping("/email")
    public ResponseEntity<?> changeEmail(@RequestBody EmailDto emailDto){
        accountService.changeEmail(emailDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto dto){
        accountService.changePassword(dto);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/profile")
    public ResponseEntity<Resource> getProfile(){

        byte[] profilePicture = accountService.getProfilePicture();
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;

        try {
            String mimeType = new Tika().detect(profilePicture);
            rs = new ByteArrayResource(profilePicture);
            header.setContentType(MediaType.parseMediaType(mimeType));
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(rs, header, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> changeProfile(@NotNull @RequestParam("file") MultipartFile file){
        accountService.changeProfilePicture(file);
        return ResponseEntity.ok().build();
    }

}
