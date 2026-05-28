package atmin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* atmin.service.WatchService.save(..)) || " +
            "execution(* atmin.service.WatchService.update(..)) || " +
            "execution(* atmin.service.WatchService.updatePatch(..))")
    public void logMethodName(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("AOP LOG - Method Name: {}", methodName);
    }
}
