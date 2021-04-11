package project.dailysup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DailysupApplication {

    private static final String PROPERTIES =
            "spring.config.location="
                    +"classpath:/application.yml"
                    +",classpath:/jwt.yml" +
                    ",classpath:/s3.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(DailysupApplication.class)
                .properties(PROPERTIES)
                .run();
    }

    //TODO: 도메인 롬복빌더 교체하기.
    //TODO: 비지니스 exception 줄이고 exception code 로 관리 + code 는 enum으로

}
