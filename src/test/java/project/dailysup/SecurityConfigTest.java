package project.dailysup;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.filter.CorsFilter;
import project.dailysup.jwt.JwtAccessDeniedHandler;
import project.dailysup.jwt.JwtAuthenticationEntryPoint;
import project.dailysup.jwt.JwtTokenProvider;

public class SecurityConfigTest {

    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    CorsFilter corsFilter;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
}
