package project.dailysup.account.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dailysup.account.exception.InvalidPasswordException;


import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class AccountRepositoryDomainTest {

    @Autowired
    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Test
    @DisplayName("loginId 중복 불가 검증")
    public void loginId_NoNo_duplication() throws Exception{
        //given
        Account accountA = getNotNullOnlyAccount("loginId", "password");
        Account accountB = getNotNullOnlyAccount("loginId", "password2");
        accountRepository.save(accountA);

        //when


        //then
        assertThrows(DataIntegrityViolationException.class,()->accountRepository.saveAndFlush(accountB));

    }

    /**
     * Validation Test
     */

    @Test()
    @DisplayName("loginId는 5자 이상 30자 이하여야 한다: 성공케이스")
    public void account_id_5to30_test () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abffdad", "abcde1234");

        //when
        accountRepository.save(account);

        Account find = accountRepository.findByLoginId("abffdad").get();

        //then
        assertThat(find.getId()).isEqualTo(account.getId());


    }

    @Test()
    @DisplayName("loginId는 5자 이상 30자 이하여야 한다: loginId가 5글자 미만인 경우")
    public void account_id_5to30_test_fail () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abf", "abcde1234");

        //then
        assertThrows(ConstraintViolationException.class, ()->accountRepository.saveAndFlush(account));
    }

    @Test()
    @DisplayName("loginId는 5자 이상 30자 이하여야 한다: loginId가 30글자 초과인 경우")
    public void account_id_5to30_test_fail2 () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abffdasfdbnakjfenajsdnfjekanfadmsfnbasdjkfbajekflb", "abcde1234");

        //then
        assertThrows(ConstraintViolationException.class, ()->accountRepository.saveAndFlush(account));
    }

    @Test()
    @DisplayName("nickname은 5자 이상 30자 이하여야 한다: 성공케이스")
    public void nickname_5to30_test () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abffdad", "abcde1234","nickname123");

        //when
        accountRepository.save(account);

        Account find = accountRepository.findByLoginId("abffdad").get();

        //then
        assertThat(find.getId()).isEqualTo(account.getId());


    }


    @Test()
    @DisplayName("nickname은 5자 이상 30자 이하여야 한다: nickname 5글자 미만인 경우")
    public void nickname_5to30_test_fail () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abdfddf", "abfdaefdaf", "ab");

        //then
        assertThrows(ConstraintViolationException.class, ()->accountRepository.saveAndFlush(account));
    }

    @Test()
    @DisplayName("nickname은 5자 이상 30자 이하여야 한다: loginId가 30글자 초과하는 경우")
    public void nickname_5to30_test_fail2 () throws Exception{
        //given
        Account account = getNotNullOnlyAccount("abssflb","fjeiosfe", "abcde123fdafewfasfdsafewafdsafefdsafefdsafdsafds4");

        //then
        assertThrows(ConstraintViolationException.class, ()->accountRepository.saveAndFlush(account));
    }


    @Test
    @DisplayName("password 변경 검증: password encoder가 null일 때")
    public void password_encoder_null_test() throws Exception{
        //then
        assertThrows(InvalidPasswordException.class,()->{
            Account.builder()
                    .loginId("loginId")
                    .password("password")
                    .nickname("nickname")
                    .isActivated(true)
                    .role(Role.USER)
                    .build();
        });
    }

    @Test
    @DisplayName("password 변경 검증: password가 null일 때")
    public void password_null_test() throws Exception{
        //then
        //then
        assertThrows(InvalidPasswordException.class,()->{
            Account.builder()
                    .loginId("loginId")
                    .password("password")
                    .nickname("nickname")
                    .isActivated(true)
                    .role(Role.USER)
                    .build();
        });
    }

    @Test
    @DisplayName("password 변경 검증: password가 빈문자열 일 때")
    public void password_empty_test() throws Exception{
        //then
        //then
        assertThrows(InvalidPasswordException.class,()->{
            Account.builder()
                    .loginId("loginId")
                    .password("")
                    .nickname("nickname")
                    .isActivated(true)
                    .role(Role.USER)
                    .build();
        });
    }

    private Account getNotNullOnlyAccount(String loginId, String password, String nickname) {
        return Account.builder()
                .loginId(loginId)
                .password(password)
                .passwordEncoder(passwordEncoder)
                .nickname(nickname)
                .isActivated(true)
                .role(Role.USER)
                .build();
    }

    private Account getNotNullOnlyAccount(String loginId, String password) {
        return Account.builder()
                .loginId(loginId)
                .password(password)
                .passwordEncoder(passwordEncoder)
                .nickname("testNickname")
                .isActivated(true)
                .role(Role.USER)
                .build();
    }
}
