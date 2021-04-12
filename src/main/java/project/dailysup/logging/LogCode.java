package project.dailysup.logging;

import java.util.List;

public enum LogCode {
    LOG_ERR("잘못된 로깅",List.of("logCode")),
    PERF_CONT("controller 의 요청-응답 간 시간 간격을 로깅",List.of("className", "methodName","duration"));



    private String desc;
    private List<String> params;

    public String getDesc() {
        return desc;
    }

    public List<String> getParams() {
        return params;
    }

    LogCode(String desc, List<String> params) {
        this.desc = desc;
        this.params = params;
    }

    @Override
    public String toString() {
        return "LogCode{" +
                "desc='" + desc + '\'' +
                ", params=" + params +
                '}';
    }
}
