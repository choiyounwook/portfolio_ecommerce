package com.portfolio.ecommerce.common.aop;

import javax.sound.midi.Soundbank;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

  @Pointcut("execution(* com.portfolio.ecommerce.domain.refund.service.RefundService.create(..))")
  public void refundProcessing() {

  }

  @AfterThrowing(pointcut = "refundProcessing()", throwing = "exception")
  public void logAndNotifyException(Exception exception) {
    log.error("환불 처리 중 예외 발생: {}", exception.getMessage(), exception);
    sendNotification(exception.getMessage());
  }


  private void sendNotification(String errorMessage) {
    System.out.println("관리자 알림 전송: " + errorMessage);
  }
}
