package project.dailysup.logging;

import com.google.cloud.storage.Acl;
import org.bouncycastle.pqc.crypto.rainbow.Layer;

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

    PERF_CONT               (DomainCode.AOP         ,LayerCode.CONTROLLER   ,1L     ,"Controller Performance Logging"        , "controller 의 요청-응답 간 시간 간격을 로깅한다." , List.of("className", "methodName","duration"))

    , CURR_ACC              (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,2L     ,"Current Account ID"                    , "account 정보를 조회한 loginId 로깅한다.", List.of("loginId"))

    , SIGN_UP               (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,3L     ,"Sign Up"                               ,"회원가입한 loginId를 로깅한다.", List.of("loginId"))

    , CREATE_TOKEN          (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,4L     , "Create Token"                         ,"토큰을 발급받은 계정의 loginId를 로깅한다.", List.of("loginId"))

    , WITHDRAW              (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,5L     , "Withdraw"                             , "회원탈퇴를 요청한 iloginId를 로깅한다.", List.of("loginId"))

    , VAL_PW_TOKEN          (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,6L     ,"Validate PW Reset Token"               ,"비밀번호 찾기 시 이메일로 받은 비밀번호 리셋 토큰이 유효한지 검증 요청의 loginId와 성공 여부를 로깅한다.", List.of("loginId, isSuccess") )

    , SEND_RESET            (DomainCode.ACCOUNT    ,LayerCode.CONTROLLER   ,7L     , "Send Reset Token"                     ,"이메일로 비밀번호 초기화 토큰을 보낼 것을 요청하는 loginId를 로깅한다.", List.of("loginId"))

    , SET_PW                (DomainCode.ACCOUNT     ,LayerCode.CONTROLLER   ,8L     , "Set Password"                         ,"비밀번호를 새롭게 설정하는 loginId를 로깅한다.", List.of("loginId"))

    , CG_PW                 (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,9L     ,"Change Email"                          ,"비밀번호를 변경하는 loginId를 로깅한다.",List.of("loginId"))

    , CG_EMAIL              (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,10L    ,"Change Email"                         ,"이메일을 변경을 변형하는 loginId를 로깅한다." ,List.of("loginId") )

    , DEL_EMAIL             (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,11L    ,"Delete Email"                         ,"이메일을 삭제하는 loginId를 로깅한다..",List.of("loginId") )

    , CG_PROFILE            (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,12L    ,"Change Profile Picture"               ,"프로필 사진을 변경하는 loginId를 로깅한다.",List.of("loginId"))

    , DEL_PROFILE           (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,13L    ,"Delete Profile Picture"               ,"프로필 사진을 삭제하는 loginId를 로깅한다.",List.of("loginId"))

    , CHANGE_NICK           (DomainCode.ACCOUNT     ,LayerCode.SERVICE      ,14L    ,"Change Nickname"                      ,"닉네임을 변경하는 loginId를 로깅한다.",List.of("loginId"))
    
    , DEACT_ACC             (DomainCode.SECURITY    ,LayerCode.SERVICE      ,15L    ,"Deactivate Account"                      ,"탈퇴된 아이디로 로그인 시도하는 loginId를 로깅한다.",List.of("loginId"));



    private DomainCode domain;
    private LayerCode layer;
    private Long code;
    private String title;
    private String desc;
    private List<String> params;

    LogCode(DomainCode domain, LayerCode layer, Long code, String title, String desc, List<String> params) {
        this.domain = domain;
        this.layer = layer;
        this.code = code;
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

    public Long getCode() {
        return code;
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
                ", code=" + code +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", params=" + params +
                '}';
    }
}
