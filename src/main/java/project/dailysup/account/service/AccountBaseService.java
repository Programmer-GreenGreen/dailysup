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
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountBaseService {

    private final AccountRepository accountRepository;

    public Account getCurrentAccount() {
        return accountRepository.findByLoginId(SecurityUtils.getCurrentLoginId())
                .orElseThrow(UserNotFoundException::new);
    }
}
