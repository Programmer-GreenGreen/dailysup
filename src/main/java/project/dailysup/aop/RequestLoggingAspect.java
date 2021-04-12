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
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class RequestLoggingAspect {


    @Around("bean(*Controller)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        Long startAt = System.currentTimeMillis();

        Object result = pjp.proceed();

        Long endAt = System.currentTimeMillis();

        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Long duration = endAt - startAt;

        log.info(LogFactory.createLog(LogCode.PERF_CONT, className, methodName, duration.toString()));
        return result;
    }


}
