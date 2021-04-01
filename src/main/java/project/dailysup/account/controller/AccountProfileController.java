package project.dailysup.account.controller;


import com.google.firebase.database.annotations.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.service.AccountInfoService;
import project.dailysup.account.service.AccountQueryService;
import project.dailysup.account.service.AccountRegisterService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/profile")
public class AccountProfileController {

    private final AccountQueryService accountQueryService;
    private final AccountInfoService accountInfoService;

    @GetMapping
    public ResponseEntity<Resource> getProfile(){

        byte[] profilePicture = accountQueryService.getProfilePicture();
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;

        String mimeType = new Tika().detect(profilePicture);
        rs = new ByteArrayResource(profilePicture);
        header.setContentType(MediaType.parseMediaType(mimeType));

        return new ResponseEntity<>(rs, header, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> changeProfile(@NotNull @RequestParam("file") MultipartFile file){
        accountInfoService.changeProfilePicture(file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(){
        accountInfoService.deleteProfilePicture();
        return ResponseEntity.ok().build();
    }


}
