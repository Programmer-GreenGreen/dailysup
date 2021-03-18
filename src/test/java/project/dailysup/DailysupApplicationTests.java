package project.dailysup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootTest(
    properties = {"spring.config.location="
            +"classpath:/application.yml"
            +",classpath:/jwt.yml"}
)
class DailysupApplicationTests {

    @Test
    void contextLoads() {
    }

}
