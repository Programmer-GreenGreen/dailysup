package project.dailysup.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class RequestLoggingAspect {

    private final ObjectMapper mapper;


    @Around("bean(*Controller)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        long startAt = System.currentTimeMillis();

        Object result = pjp.proceed();

        long endAt = System.currentTimeMillis();

        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        long duration = endAt - startAt;


        PerformanceLogging logObject = PerformanceLogging.builder()
                .title("Controller Performance Logging")
                .classname(className)
                .methodName(methodName)
                .executionTime(Duration.ofMillis(duration))
                .build();


        log.info(mapper.writeValueAsString(logObject));

        return result;
    }

    //TODO: title enum으로 바꾸기
    @Getter
    static class PerformanceLogging{
        private String title;
        private String classname;
        private String methodName;
        private Duration executionTime;

        @Builder
        public PerformanceLogging(String title, String classname, String methodName, Duration executionTime) {
            this.title = title;
            this.classname = classname;
            this.methodName = methodName;
            this.executionTime = executionTime;
        }
    }

    public RequestLoggingAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
