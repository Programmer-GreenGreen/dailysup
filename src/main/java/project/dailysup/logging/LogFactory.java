package project.dailysup.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class LogFactory {


    public static String createLog(LogCode logCode, String... obj){

        List<String> params = logCode.getParams();

        if(obj.length != params.size()){
            log.error(logCode.toString());
            return "";
        }

        return createJson(params, obj);
    }

    private static String createJson(List<String> keys, String[] values) {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < keys.size(); i++) {
            sb.append("\"").append(keys.get(i)).append("\"");
            sb.append(":");
            sb.append("\"").append(values[i]).append("\"");

            if(i != keys.size()-1){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }


}
