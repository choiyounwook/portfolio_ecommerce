package com.portfolio.ecommerce.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExcutionTimeAspect {

  @Pointcut("execution(* com.portfolio.ecommerce.domain.order.service.OrderService.create(..))")
  public void orderProcessing() {
  }

  public Object orderMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    log.info("주문 처리 시작: {}", joinPoint.getSignature());

    Object result = joinPoint.proceed();

    long endTime = System.currentTimeMillis();
    log.info("주문 처리 완료: {} 실행 시간: {} ms", joinPoint.getSignature(), (endTime - startTime));

    return result;
  }
}
