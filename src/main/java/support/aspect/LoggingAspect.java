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
	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	
	@Pointcut("within(net.slipp.web..*) || within(net.slipp.service..*)")
    private void loggingPointcut() {}

    @Around("support.aspect.LoggingAspect.loggingPointcut()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
    	log.debug("[{}] - arguments : {}", pjp.getSignature(), pjp.getArgs());
    	Object returnValue = pjp.proceed();
    	log.debug("[{}] - return value : {}", pjp.getSignature(), returnValue);
    	return returnValue;
    }
}
