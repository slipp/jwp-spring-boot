package support.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	@Pointcut("within(net.slipp.web..*) || within(net.slipp.service..*)")
    public void loggingPointcut() {}

    @Around("loggingPointcut()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        final Logger log = LoggerFactory.getLogger(pjp.getTarget().getClass());
        
        final String methodName = pjp.getSignature().getName();
        if (!isUtilMethod(methodName)) {
            log.debug("{}(): {}", methodName, pjp.getArgs());
        }
        
    	Object result = pjp.proceed();
    	
    	if (!isUtilMethod(methodName)) {
    	    log.debug("{}(): result={}", methodName, result);
    	}
    	return result;
    }
    
    private boolean isUtilMethod(String name) {
        return name.startsWith("get")
                || name.startsWith("set")
                || name.equals("toString")
                || name.equals("equals")
                || name.equals("hashCode");
    }
}
