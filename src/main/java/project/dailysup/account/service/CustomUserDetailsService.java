package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.exception.UserNotFoundException;
import project.dailysup.jwt.NotActivatedException;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

   private final AccountRepository accountRepository;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String loginId) {
      return accountRepository.findByLoginId(loginId)
         .map(account -> createDetails(loginId, account))
         .orElseThrow(() -> new UserNotFoundException(loginId + ": 존재하지 않는 ID입니다."));
   }

   private User createDetails(String loginId, Account account) {
      if (!account.getIsActivated()) {
         log.info(LogFactory.create(LogCode.DEACT_ACC, loginId));
         throw new NotActivatedException(loginId + ": 활성화되어 있지 않습니다.");
      }
      SimpleGrantedAuthority role = new SimpleGrantedAuthority(account.getRole().toString());
      List<GrantedAuthority> grantedAuthorities = Collections.singletonList(role);


      return new User(account.getLoginId(),
              account.getPassword(),
              grantedAuthorities);
   }
}
