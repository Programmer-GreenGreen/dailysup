package project.dailysup.logging;

import com.google.cloud.storage.Acl;

import java.util.List;

/**
 * json 타입의 로깅을 object mapper 로 구현하지 않은 이유
 * : 모든 로그마다 클래스를 정의하지 않고 enum 타입 추가만으로 로깅하기 위해
 * : LogFactory 는 factory 패턴을 응용해 log 문자열 생성
 */

public enum LogCode {

    /**
     * Json 형태로 로깅한다.
     *
     * Parameters
     * 1. Domain Code : 어느 도메인에서 로깅하는 지 명시한다.
     * 2. Layer Code : 어느 레이어에서 로깅하는 지 명시한다.
     * 3. Title : Log 의 제목
     * 4. Desc : log 에 대한 설명, log 에는 포함되어 출력되지 않는다.
     * 5. Params : json 의 키 값이다.
     */

    PERF_CONT       (DomainCode.AOP,        LayerCode.CONTROLLER,"Controller Performance Logging",  "controller 의 요청-응답 간 시간 간격을 로깅한다.",      List.of("className", "methodName","duration"))

    ,CURR_ACC       (DomainCode.ACCOUNT,    LayerCode.CONTROLLER,"Current Account ID",              "account 정보를 조회한 id 로깅한다.",                 List.of("loginId"))

    ,SIGN_UP        (DomainCode.ACCOUNT,    LayerCode.CONTROLLER,"Sign Up",                         "회원가입한 id를 로깅한다.",                          List.of("loginId"))

    ,CREATE_TOKEN   (DomainCode.ACCOUNT,    LayerCode.CONTROLLER, "Create Token",                   "토큰을 발급받은 계정의 id를 로깅한다.",                 List.of("loginId"))

    ,WITHDRAW       (DomainCode.ACCOUNT,    LayerCode.CONTROLLER, "Withdraw",                       "회원탈퇴를 요청한 id를 로깅한다.",                     List.of("loginId"))

    ;



    private DomainCode domain;
    private LayerCode layer;
    private String title;
    private String desc;
    private List<String> params;

    LogCode(DomainCode domain, LayerCode layer, String title, String desc, List<String> params) {
        this.domain = domain;
        this.layer = layer;
        this.title = title;
        this.desc = desc;
        this.params = params;
    }

    public DomainCode getDomain() {
        return domain;
    }

    public LayerCode getLayer() {
        return layer;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "LogCode{" +
                "domain=" + domain +
                ", layer=" + layer +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", params=" + params +
                '}';
    }

}
