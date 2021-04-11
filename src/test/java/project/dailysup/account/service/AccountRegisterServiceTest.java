package project.dailysup.account.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.domain.Role;
import project.dailysup.account.dto.SignUpResponseDto;
import project.dailysup.account.exception.DuplicatedAccountException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AccountRegisterServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    AccountRegisterService accountRegisterService;


    @Test
    @DisplayName("회원가입 테스트 : 성공")
    public void sign_up_test() throws Exception {
        //given
        Long accountId = 1L;
        String loginId = "testLoginId";
        String password = "password";
        String nickName = "nickname";

        Account account = Account.builder()
                .loginId(loginId)
                .passwordEncoder(passwordEncoder)
                .password(password)
                .nickname(nickName)
                .role(Role.USER)
                .isActivated(true)
                .build();
        ReflectionTestUtils.setField(account, "id", accountId);

        given(accountRepository.save(any())).willReturn(account);

        //when
        SignUpResponseDto dto = accountRegisterService.singUp(loginId, password, nickName);

        //then
        assertThat(dto.getLoginId()).isEqualTo(loginId);
        assertThat(dto.getNickname()).isEqualTo(nickName);

    }

    @Test
    @DisplayName("회원가입 테스트 : 중복된 id 실패")
    public void sign_up_test_fail() throws Exception {
        //given
        Long accountId = 1l;
        String loginId = "testLoginId";
        String password = "password";
        String nickName = "nickname";

        Account account = Account.builder()
                .loginId(loginId)
                .passwordEncoder(passwordEncoder)
                .password(password)
                .nickname(nickName)
                .role(Role.USER)
                .isActivated(true)
                .build();
        ReflectionTestUtils.setField(account, "id", accountId);

        given(accountRepository.findByLoginId(loginId)).willReturn(Optional.empty());
        accountRegisterService.singUp(loginId, password, nickName);
        given(accountRepository.findByLoginId(loginId)).willReturn(Optional.of(account));

        //then
        assertThrows(DuplicatedAccountException.class, () -> accountRegisterService.singUp(loginId, password, nickName));
    }

    //withdraw는 domain에서 테스트했음.
}