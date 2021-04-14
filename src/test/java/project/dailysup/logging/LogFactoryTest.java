package project.dailysup.logging;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogFactoryTest {

    @Test
    @DisplayName("create Json Test")
    public void create_json_test() throws Exception{
        //given
        Long code = 1L;
        String className = "testClass";
        String methodName = "testMethod";
        String duration = "123mils";

        JsonObject json = new JsonObject();
        json.addProperty("domain", DomainCode.AOP.toString() );
        json.addProperty("layer", LayerCode.CONTROLLER.toString());
        json.addProperty("code", code.toString());
        json.addProperty("className", className);
        json.addProperty("methodName", methodName);
        json.addProperty("duration", duration);

        //when
        String log = LogFactory.create(LogCode.PERF_CONT, className, methodName, duration);
        JsonObject logJson = JsonParser.parseString(log).getAsJsonObject();

        //then

        assertTrue(json.equals(logJson));


    }

}
