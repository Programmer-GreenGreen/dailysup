package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.controller.AccountInfoController;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.domain.Role;
import project.dailysup.account.dto.*;
import project.dailysup.account.exception.DuplicatedAccountException;
import project.dailysup.account.exception.NotValidWithdrawRequest;
import project.dailysup.account.exception.UserNotFoundException;
import project.dailysup.jwt.JwtTokenProvider;
import project.dailysup.util.SecurityUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountRegisterService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto singUp(String loginId, String password, String nickname){

        validateDuplicationAccount(loginId);

        Account saveAccount = Account.builder()
                .loginId(loginId)
                .passwordEncoder(passwordEncoder)
                .password(password)
                .nickname(nickname)
                .role(Role.USER)
                .isActivated(true)
                .build();

        accountRepository.save(saveAccount);

        return SignUpResponseDto.builder()
                                .loginId(loginId)
                                .nickname(nickname)
                                .build();
    }

    public LoginIdDto withdraw(String loginId, String password){
        Account currentAccount = getCurrentAccount();

        /**
         * Vaildation Login은 도메인 안으로 변경.
         */

        //validateWithdrawRequest(loginId, password, currentAccount);
        //accountRepository.deleteByLoginId(loginId);

        currentAccount.changeToNotActivated(loginId, password, passwordEncoder);
        return LoginIdDto.of(loginId);

    }








    private Account getCurrentAccount() {
        return accountRepository.findByLoginId(SecurityUtils.getCurrentLoginId())
                .orElseThrow(UserNotFoundException::new);
    }




    private void validateDuplicationAccount(String loginId) {
        if(accountRepository.findByLoginId(loginId).orElse(null) != null){

            throw new DuplicatedAccountException();
        }
    }

}
