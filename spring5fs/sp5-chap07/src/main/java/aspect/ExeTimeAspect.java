package aspect;

import java.util.Arrays;

import aspect.CommonPointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class ExeTimeAspect {
	
	@Pointcut("execution(public * chap07..*(..))")	// 공통 기능을 적용할 대상
	private void publicTarget() {
	}
	
	@Around("CommonPointcut.commonTarget()")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed();	// 대상 객체 메소드 실행
			return result;
		} finally {
			long finish = System.nanoTime();
			Signature sig = joinPoint.getSignature();	// getSignature(): 호출한 메소드의 시그니처
			
			System.out.printf("%s.%s(%s) 실행 시간: %d ns\n",
					joinPoint.getTarget().getClass().getSimpleName(),	// getTarget(): 대상 객체
					sig.getName(), Arrays.toString(joinPoint.getArgs()),	// getArgs(): 인자 목록
					(finish - start));
		}
	}
	
}
