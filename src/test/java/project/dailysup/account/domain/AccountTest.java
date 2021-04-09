package project.dailysup.account.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dailysup.device.domain.Device;
import project.dailysup.histroy.domain.History;
import project.dailysup.item.domain.Item;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static final String testLoginId = "testLoginId";
    static final String testPassword = "testPassword";
    static final String testEmail = "test@test.com";
    static final String testNickname = "nickname";
    static final String testURl = "someUrl";

    @Test
    @DisplayName("Account 최소 생성 테스트")
    public void create_account_test_1 () throws Exception{
        //given

        Account createdAccount = createAccount(testLoginId, testPassword, testNickname);
        //when
        String loginId_out = createdAccount.getLoginId();
        String password_out = createdAccount.getPassword();
        String nickName_out = createdAccount.getNickname();

        //then

        assertThat(testLoginId).isEqualTo(loginId_out);
        assertThat(testPassword).isNotEqualTo(password_out);
        assertTrue(passwordEncoder.matches(testPassword,password_out));
        assertThat(testNickname).isEqualTo(nickName_out);
    }

    private Account createAccount(String loginId_in, String password_in, String nickname_in) {
        return Account.builder()
                .loginId(loginId_in)
                .password(password_in)
                .passwordEncoder(passwordEncoder)
                .nickname(nickname_in)
                .build();
    }

    @Test
    @DisplayName("Account 최대 생성 테스트")
    public void create_account_test_2() throws Exception{
        //given

        Account account = Account.builder()
                .loginId(testLoginId)
                .password(testPassword)
                .passwordEncoder(passwordEncoder)
                .email(testEmail)
                .nickname(testNickname)
                .profilePictureUrl(testURl)
                .role(Role.USER)
                .isActivated(true)
                .build();

        account.addDevice(new Device("fcmtoken1",account));
        account.addDevice(new Device("fcmtoken2",account));
        account.removeDevice("fcmtoken2");

        account.addItem(new Item("testItem", LocalDate.now(),18));
        account.addItem(new Item("testItem2", LocalDate.now(), 18));

        account.changeToNotActivated(testLoginId, testPassword, passwordEncoder);

        //when
        String nickname = account.getNickname();
        String email = account.getEmail();
        String profilePictureUrl = account.getProfilePictureUrl();
        Role role = account.getRole();
        Boolean isActivated = account.getIsActivated();
        //then

        assertThat(nickname).isEqualTo("nickname");
        assertThat(email).isEqualTo("test@test.com");
        assertThat(profilePictureUrl).isEqualTo("someUrl");
        assertThat(role).isEqualTo(Role.USER);
        assertFalse(isActivated);

        assertThat(account.getDeviceList())
                .extracting("fcmToken")
                .containsOnly("fcmtoken1");
    }
}