package project.dailysup.account.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.dailysup.account.service.AccountQueryService;
import project.dailysup.account.service.AccountRegisterService;

@WebMvcTest
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountQueryService queryService;
    AccountRegisterService registerService;


    @Test
    @DisplayName("회원가입 테스트")
    public void sign_up_test() throws Exception {



    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    public void withdraw_test() throws Exception {



    }

    @Test
    @DisplayName("현재 계정 조회 테스트")
    public void current_account_test() throws Exception {



    }
}
