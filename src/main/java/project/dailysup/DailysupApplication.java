package project.dailysup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class DailysupApplication {

    private static final String PROPERTIES =
            "spring.config.location="
                    +"classpath:/application.yml"
                    +",classpath:/jwt.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(DailysupApplication.class)
                .properties(PROPERTIES)
                .run();
    }

}
