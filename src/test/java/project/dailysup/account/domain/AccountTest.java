package project.dailysup.account.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Account Entity 의 테스트 코드
 * 타 엔티티와 연관된 메서드나 Validation 테스트는
 * 리포지토리 테스트에서 실행한다.
 */

class AccountTest extends AccountTestBase {


    @Test
    @DisplayName("Account 생성 테스트: 생성자(빌더)와 getter 를 테스트한다.")
    public void create_account_test() throws Exception{
        //given

        Account account = createDefaultAccount();


        //when
        String loginId_out = account.getLoginId();
        String password_out = account.getPassword();
        String nickName_out = account.getNickname();

        String nickname = account.getNickname();
        String email = account.getEmail();
        String profilePictureUrl = account.getProfilePictureUrl();
        Role role = account.getRole();


        //then
        assertThat(nickname).isEqualTo("nickname");
        assertThat(email).isEqualTo("test@test.com");
        assertThat(profilePictureUrl).isEqualTo("someUrl");
        assertThat(role).isEqualTo(Role.USER);

        assertThat(testLoginId).isEqualTo(loginId_out);
        assertThat(testPassword).isNotEqualTo(password_out);
        assertTrue(passwordEncoder.matches(testPassword,password_out));
        assertThat(testNickname).isEqualTo(nickName_out);

        assertThat(account.getItemList())
                .extracting("title")
                .containsExactlyInAnyOrder(testItem1, testItem2);
        assertThat(account.getDeviceList())
                .extracting("fcmToken")
                .containsExactlyInAnyOrder(testToken1, testToken2);

    }

    @Test
    @DisplayName("Reset Token 테스트")
    public void reset_token_test() throws Exception{
        //given
        Account account = createDefaultAccount();
        String code = "reset";
        LocalDateTime expire = LocalDateTime.of(2021, Month.JULY, 12, 2, 11);

        //when

        account.setResetCode(code, expire);
        Account.ResetToken resetToken = account.getResetToken();

        //then
        assertThat(resetToken.getResetCode()).isEqualTo("reset");
        assertThat(resetToken.getExpire()).isEqualTo(expire);

    }


    @Test
    @DisplayName("회원 탈퇴 테스트: 탈퇴 후에는 activated가 false여야 한다.")
    public void withdraw_test () throws Exception{
        //given
        Account account = createDefaultAccount();

        //when
        account.changeToNotActivated(testLoginId, testPassword, passwordEncoder);
        Boolean isActivated = account.getIsActivated();

        //then

        assertFalse(isActivated);
    }

    @Test
    @DisplayName("비밀번호 재설정 테스트")
    public void set_password_test() throws Exception{
        //given
        Account account = createDefaultAccount();
        String newPassword = "newPassword";

        //when
        account.setPassword(passwordEncoder, newPassword);

        //then
        assertTrue(passwordEncoder.matches(newPassword, account.getPassword()));

    }


    @Test
    @DisplayName("비밀번호 변경 테스트")
    public void change_password_test() throws Exception{
        //given
        Account account = createDefaultAccount();
        String oldPassword = testPassword;
        String newPassword = "newPassword";

        //when
        account.changePassword(passwordEncoder, oldPassword, newPassword);

        //then
        assertTrue(passwordEncoder.matches(newPassword, account.getPassword()));

    }


}