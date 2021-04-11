package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.exception.UserNotFoundException;
import project.dailysup.util.SecurityUtils;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountQueryService extends AccountBaseService {

    private final ProfileFileService profileFileService;

    public Account findCurrentAccount(){
        return getCurrentAccount();
    }

    public String getEmail() {
        Account findAccount = getCurrentAccount();
        return findAccount.getEmail();
    }

    public String getNickname(){
        Account findAccount = getCurrentAccount();
        return findAccount.getNickname();
    }

    public byte[] getProfilePicture(){
        Account currentAccount = getCurrentAccount();

        return profileFileService.download(currentAccount.getProfilePictureUrl());
    }





    public AccountQueryService(AccountRepository accountRepository, ProfileFileService profileFileService) {
        super(accountRepository);
        this.profileFileService = profileFileService;
    }
}
