package project.dailysup.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import project.dailysup.logging.LogCode;
import project.dailysup.logging.LogFactory;

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

        log.info(LogFactory.create(LogCode.PERF_CONT, className, methodName, duration.toString()+"ms"));
        return result;
    }


}
