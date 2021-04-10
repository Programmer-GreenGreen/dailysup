package project.dailysup.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dailysup.device.domain.Device;
import project.dailysup.item.domain.Item;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class AccountRepositoryQueryMethodTest {

    @Autowired
    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("login Id로 Account 찾기: 일치하는 id가 있는 경우")
    public void find_by_loginId() throws Exception{
        //given

        //when
        Optional<Account> account = accountRepository.findByLoginId("testIdA");

        //then
        assertTrue(account.isPresent());
        assertThat(account.get().getLoginId()).isEqualTo("testIdA");
        assertThat(account.get().getEmail()).isEqualTo("testA@test.com");
    }

    @Test
    @DisplayName("login Id로 Account 찾기: 일치하는 id가 없는  경우")
    public void find_by_loginId_fail() throws Exception{
        //given

        //when
        Optional<Account> account = accountRepository.findByLoginId("WeirdId");

        //then
        assertFalse(account.isPresent());
    }

    @Test
    @DisplayName("email로 Account 찾기: 일치하는 email이 있는 경우")
    public void find_by_email() throws Exception{
        //given

        //when
        Optional<Account> account = accountRepository.findByEmail("testA@test.com");

        //then
        assertTrue(account.isPresent());
        assertThat(account.get().getLoginId()).isEqualTo("testIdA");
        assertThat(account.get().getEmail()).isEqualTo("testA@test.com");
    }

    @Test
    @DisplayName("email로 Account 찾기: 일치하는 email이 없는 경우")
    public void find_by_email_fail() throws Exception{
        //given

        //when
        Optional<Account> account = accountRepository.findByEmail("real@real.com");

        //then
        assertFalse(account.isPresent());
    }

    @Test
    @DisplayName("loginId로 Account 삭제하기")
    public void delete_by_loginId_test () throws Exception{
        //given
        Account account = accountRepository.findByLoginId("testIdA").get();

        //when
        accountRepository.deleteById(account.getId());

        //then
        Optional<Account> deletedAccount = accountRepository.findByLoginId(account.getLoginId());
        assertThat(deletedAccount.isPresent()).isFalse();

        assertThat(accountRepository.findAll())
                .extracting("loginId")
                .containsExactlyInAnyOrder("testIdB", "testIdC");
    }





    @BeforeEach
    public void addAccount(){
        Account accountA = Account.builder()
                .loginId("testIdA")
                .password("testPasswordA")
                .passwordEncoder(passwordEncoder)
                .email("testA@test.com")
                .nickname("testNicknameA")
                .profilePictureUrl("testURlA")
                .role(Role.USER)
                .isActivated(true)
                .build();
        accountRepository.save(accountA);

        Account accountB = Account.builder()
                .loginId("testIdB")
                .password("testPasswordB")
                .passwordEncoder(passwordEncoder)
                .email("testB@test.com")
                .nickname("testNicknameB")
                .profilePictureUrl("testURlB")
                .role(Role.USER)
                .isActivated(true)
                .build();
        accountRepository.save(accountB);

        Account accountC = Account.builder()
                .loginId("testIdC")
                .password("testPasswordC")
                .passwordEncoder(passwordEncoder)
                .email("testC@test.com")
                .nickname("testNicknameC")
                .profilePictureUrl("testURlC")
                .role(Role.USER)
                .isActivated(true)
                .build();
        accountRepository.save(accountC);


        accountA.addDevice(new Device("testTokenA1",accountA));
        accountA.addDevice(new Device("testTokenA2",accountA));
        accountA.addItem(new Item("testItemA1", LocalDate.now(),18));
        accountA.addItem(new Item("testItemA2", LocalDate.now(), 21));



        accountB.addDevice(new Device("testTokenB1",accountB));
        accountB.addDevice(new Device("testTokenB2",accountB));
        accountB.addItem(new Item("testItemB1", LocalDate.now(),18));
        accountB.addItem(new Item("testItemB2", LocalDate.now(), 21));



        accountC.addDevice(new Device("testTokenC1",accountC));
        accountC.addDevice(new Device("testTokenC2",accountC));
        accountC.addItem(new Item("testItemC1", LocalDate.now(),18));
        accountC.addItem(new Item("testItemC2", LocalDate.now(), 21));




    }
}