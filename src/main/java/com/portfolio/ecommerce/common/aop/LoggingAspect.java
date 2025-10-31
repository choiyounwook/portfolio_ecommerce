package com.portfolio.ecommerce.common.aop;

import com.portfolio.ecommerce.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Before("execution(* com.sparta.week02_project.domain..*(..))")
  public void logBefore() {
    log.info("메서드 실행 전 로그");
  }

  @Before("execution(* com.sparta.week02_project.domain..*(..))")
  public void logMethodDetails(JoinPoint joinPoint) {
    log.info("실행된 메서드 이름:{}", joinPoint.getSignature().getName());
    Object[] args = joinPoint.getArgs();

    if (args != null && args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        log.info("전달된 파라미터 [{}]: {}", i, args[i]);
      }
    }
  }

  @Before("within(com.sparta.week02_project.domain..*)")
  public void logBeforeWithin() {
    log.info("[whithin] 메서드 실행 전 로그");
  }

  @Before("@annotation(com.portfolio.ecommerce.common.annotation.Loggable)")
  public void logBeforeAnnotation() {
    log.info("[annotation] 메서드 실행 전 로그");
  }

  @Pointcut("execution(* com.sparta.week02_project.domain..service..*(..))")
  public void serviceMethods() {

  }

  @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
  public void logException(ServiceException exception) {
    log.error("AfterThrowing :[{}] {}", exception.getCode(), exception.getMessage());
  }

  @Around("serviceMethods()")
  public Object measureExcutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long endTime = System.currentTimeMillis();
    log.info("{} 메서도 실행 시간: {} ms", joinPoint.getSignature(), (endTime - startTime));
    return result;
  }
}
