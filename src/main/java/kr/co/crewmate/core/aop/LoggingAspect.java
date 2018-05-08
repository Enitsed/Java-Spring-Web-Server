package kr.co.crewmate.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * 
 * @author: 정슬기
 * @date: 2018.03.28
 * @function: 서비스 로그 및 수행시간 확인 
 */
public class LoggingAspect {
	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	
	public Object serviceChecker(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String className = joinPoint.getSignature().getDeclaringType().getName();
		String serviceName = className.substring(className.lastIndexOf(".") + 1, className.length());
		String methodName = joinPoint.getSignature().getName();
		
		String serviceMethodName = serviceName + "." + methodName + "()";
		if (log.isDebugEnabled()) {
			log.debug("### {} : 메소드시작", serviceMethodName);
		}
		
		StopWatch stopwatch = new StopWatch();
		try {
			stopwatch.start();
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Exception e) {
			throw e;
		} finally {
			stopwatch.stop();
			if(log.isDebugEnabled()) {
				log.debug("### {} : 메소드종료(runtime: {})", serviceMethodName, stopwatch.getTotalTimeMillis());
			}
		}
		
	}
	
}
