package project.dailysup.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.controller.AccountInfoController;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

@Slf4j
@Service
@Transactional
public class AccountInfoService extends AccountBaseService{

    private final ProfileFileService profileFileService;
    private final PasswordEncoder passwordEncoder;

    /**
     *  이메일 변경, 삭제 서비스
     */
    public void changeEmail(AccountInfoController.EmailDto emailDto) {
        Account findAccount = getCurrentAccount();
        findAccount.changeEmail(emailDto.getEmail());
        log.info(LogFactory.create(LogCode.CG_EMAIL, findAccount.getLoginId()));
    }

    public void deleteEmail(){
        Account findAccount = getCurrentAccount();
        findAccount.changeEmail("");
        log.info(LogFactory.create(LogCode.DEL_EMAIL, findAccount.getLoginId()));
    }

    public void changePassword(String oldPassword, String newPassword){
        Account findAccount = getCurrentAccount();
        findAccount.changePassword(passwordEncoder, oldPassword, newPassword);
        log.info(LogFactory.create(LogCode.CG_PW, findAccount.getLoginId()));
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
        log.info(LogFactory.create(LogCode.CG_PROFILE, findAccount.getLoginId()));
    }

    public void deleteProfilePicture(){
        Account findAccount = getCurrentAccount();
        String profileUrl = findAccount.getProfilePictureUrl();

        if(StringUtils.hasText(profileUrl)) {
            profileFileService.delete(profileUrl);
        }
        log.info(LogFactory.create(LogCode.DEL_PROFILE, findAccount.getLoginId()));
    }


    public void changeNickname(String nickname) {
        Account findAccount = getCurrentAccount();
        findAccount.changeNickname(nickname);
        log.info(LogFactory.create(LogCode.CHANGE_NICK, findAccount.getLoginId()));
    }

    public AccountInfoService(AccountRepository accountRepository, ProfileFileService profileFileService, PasswordEncoder passwordEncoder) {
        super(accountRepository);
        this.profileFileService = profileFileService;
        this.passwordEncoder = passwordEncoder;
    }
}
