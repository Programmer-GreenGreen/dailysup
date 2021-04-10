package project.dailysup;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 보시는 것과 같이
 * 통합 테스트마다 주절주절 안하기 위한
 * 어노테이션입니다 ヘ(^_^ヘ)
 */

@Transactional
@SpringBootTest(properties = "spring.config.location="
        +"classpath:/application.yml"
        +",classpath:/jwt.yml" +
        ",classpath:/s3.yml")
@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
