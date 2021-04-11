package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.exception.NotValidResetTokenException;
import project.dailysup.account.exception.UserNotFoundException;
import project.dailysup.common.exception.InternalErrorException;
import project.dailysup.mail.MailDto;
import project.dailysup.mail.MailService;

import java.time.LocalDateTime;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountForgetService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private static final int MAIL_EXPIRE_MINUTE = 5;
    private static final int CODE_LENGTH = 6;


    public void sendTokenByEmail(String loginId) {
        Account account = accountRepository
                .findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        String email = account.getEmail();

        if(StringUtils.hasText(email)){
            String code = createResetCode(CODE_LENGTH);
            account.setResetCode(code, LocalDateTime.now().plusMinutes(MAIL_EXPIRE_MINUTE));

            MailDto dto = new MailDto(email, "비밀번호 변경 메일", "code: " + code);

            try {
                mailService.send(dto);
            } catch (MailException e) {
                throw new InternalErrorException("메일 발송 오류!");
            }
        }
    }

    public boolean validateResetToken(String loginId, String token){

        return validateToken(loginId,token);
    }



    private boolean validateToken(String loginId, String token){
        Account account = accountRepository
                .findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        boolean isSameCode = account.getResetToken().getResetCode().equals(token);
        boolean isNotExpired = account.getResetToken().getExpire().isAfter(LocalDateTime.now());
        return isSameCode && isNotExpired;
    }


    public void setPassword(String loginId, String password, String token) {
        if(!validateToken(loginId, token)){
            throw new NotValidResetTokenException();
        }
        Account account = accountRepository
                .findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        account.changePassword(passwordEncoder, password);

    }

    private static String createResetCode(int length){
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(base.length());
            sb.append(base.charAt(idx));
        }
        return sb.toString();
    }
}
