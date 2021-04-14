package project.dailysup.logging;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class LogFactory {


    public static String create(LogCode logCode, String... values){

        List<String> params = logCode.getParams();
        String domain = logCode.getDomain().toString();
        String layer = logCode.getLayer().toString();
        Long code = logCode.getCode();

        /**
         * logCode에 정의된 인자와 넘어온 인자가 다른 경우
         */
        if(values.length != params.size()){
            log.error(logCode.toString());
            return "";
        }

        return createJson(domain, layer, code, params, values);
    }

    private static String createJson(String         domain,
                                     String         layer,
                                     Long           code,
                                     List<String>   keys,
                                     String[]       values)
    {

        JsonObject json = new JsonObject();
        json.addProperty("domain",domain);
        json.addProperty("layer",layer);
        json.addProperty("code",code.toString());

        /**
         *  로그 별로 상이한 key와 value 를
         *  json 객체에 포함시킨다.
         */
        for (int i = 0; i < keys.size(); i++) {
            json.addProperty(keys.get(i), values[i]);
        }

        return json.toString();
    }


}
