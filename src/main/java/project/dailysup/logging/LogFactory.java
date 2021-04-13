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
        String title = logCode.getTitle();

        /**
         * logCode에 정의된 인자와 넘어온 인자가 다른 경우
         */
        if(values.length != params.size()){
            log.error(logCode.toString());
            return "";
        }

        return createJson(domain, layer, title, params, values);
    }

    private static String createJson(String         domain,
                                     String         layer,
                                     String         title,
                                     List<String>   keys,
                                     String[]       values)
    {

        JsonObject json = new JsonObject();
        json.addProperty("domain",domain);
        json.addProperty("layer",layer);
        json.addProperty("title",title);

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
