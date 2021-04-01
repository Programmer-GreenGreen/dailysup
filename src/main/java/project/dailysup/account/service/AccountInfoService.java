package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.controller.AccountInfoController;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;

@Slf4j
@Service
@Transactional
public class AccountInfoService extends AccountBaseService{

    private final ProfileFileService profileFileService;
    private final PasswordEncoder passwordEncoder;

    public void changeEmail(AccountInfoController.EmailDto emailDto) {
        Account findAccount = getCurrentAccount();
        findAccount.changeEmail(emailDto.getEmail());
    }

    public void deleteEmail(){
        Account findAccount = getCurrentAccount();
        findAccount.changeEmail("");
    }

    public void changePassword(AccountInfoController.PasswordDto dto){
        Account findAccount = getCurrentAccount();
        findAccount.changePassword(passwordEncoder, dto.getPassword());
    }

    public void changeProfilePicture(MultipartFile profilePicture){
        Account findAccount = getCurrentAccount();

        String oldProfileUrl = findAccount.getProfilePictureUrl();

        if(!StringUtils.hasText(oldProfileUrl)) {
            //기존의 프로필 사진이 없는 경우 upload
            String uploadedPictureUrl = profileFileService.upload(profilePicture);
            findAccount.changeProfile(uploadedPictureUrl);
        }else {
            profileFileService.modify(oldProfileUrl, profilePicture);
        }
    }

    public void deleteProfilePicture(){
        Account findAccount = getCurrentAccount();

        String profileUrl = findAccount.getProfilePictureUrl();

        if(StringUtils.hasText(profileUrl)) {
            profileFileService.delete(profileUrl);
        }
    }


    public AccountInfoService(AccountRepository accountRepository, ProfileFileService profileFileService, PasswordEncoder passwordEncoder) {
        super(accountRepository);
        this.profileFileService = profileFileService;
        this.passwordEncoder = passwordEncoder;
    }
}
