package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.base.context.ContextHolder;
import com.example.demo.base.event.BaseEvent;
import com.example.demo.domain.account.aggregate.MoneyAccount;
import com.example.demo.domain.account.service.MoneyAccountService;
import com.example.demo.util.JsonParseUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class EventSourcingInterceptor {

	@Autowired
	private MoneyAccountService moneyAccountService;

	/**
	 * 定義切入點，針對 MoneyAccountRepository 的 save 方法進行切入。
	 */
	@Pointcut("execution(* com.example.demo.infra.repository.MoneyAccountRepository.save(..))")
	public void pointCut() {

	}

	/**
	 * 對 MoneyAccount 領域資料 執行 EventSourcing
	 * 
	 * @param joinPoint 切入點
	 * @return 方法執行結果
	 * @throws Throwable 例外
	 */
	@Around("pointCut()")
	public Object executeEventSourcing(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			Object[] args = joinPoint.getArgs();
	
			MoneyAccount entity = (MoneyAccount) args[0]; // 獲取 MoneyAccount
			log.info("[execute EventSourcing] Entity -> uuid:{}, username:{}, email:{}, balance:{}", entity.getUuid(),
					entity.getUsername(), entity.getEmail(), entity.getBalance());
	
			BaseEvent event = ContextHolder.getEvent();
			log.info("eventLog: {}, targetId:{}", event.getEventLogUuid(), event.getTargetId());
		
			// 紀錄 EventSource
			String eventStreamId = entity.getClass().getSimpleName() + "-" + entity.getUuid();
			moneyAccountService.addEventSource(eventStreamId, JsonParseUtil.serialize(entity));
	
			// 執行後續流程
			return joinPoint.proceed();

		} catch (Exception e) {
			log.error("發生錯誤:", e);
		}
		
		return joinPoint.proceed();
	}

	/**
	 * 在 PostCut 後執行
	 * 
	 * @param joinPoint 切入點
	 */
	@After("pointCut()")
	public void after(JoinPoint joinPoint) {

	}
}