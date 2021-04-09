package project.dailysup.account.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.dailysup.device.domain.Device;
import project.dailysup.item.domain.Item;

import java.time.LocalDate;

public class AccountTestBase {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final String testLoginId = "testLoginId";
    final String testPassword = "testPassword";
    final String testEmail = "test@test.com";
    final String testNickname = "nickname";
    final String testURl = "someUrl";
    final String testToken1 = "fcmtoken1";
    final String testToken2 = "fcmtoken2";
    String testItem1 = "testItem1";
    String testItem2 = "testItem2";

    public Account createDefaultAccount() {
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


        account.addDevice(new Device(testToken1,account));

        account.addDevice(new Device(testToken2,account));


        account.addItem(new Item(testItem1, LocalDate.now(),18));

        account.addItem(new Item(testItem2, LocalDate.now(), 18));
        return account;
    }
}
