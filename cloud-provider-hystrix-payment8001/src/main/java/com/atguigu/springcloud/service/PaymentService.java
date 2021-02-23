package com.atguigu.springcloud.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import cn.hutool.core.util.IdUtil;

@Service
public class PaymentService {
	public String paymentInfo_OK(Integer id) {
		return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id: " + id
				+ "\t" + "O(n_n)哈哈~";
	}
	@HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
	})
	public String paymentInfo_Timeout(Integer id) {
		int timeNumber = 5;
		try {
			TimeUnit.SECONDS.sleep(timeNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout, id: " + id
				+ "\t" + "O(n_n)哈哈~耗时（秒）：" + timeNumber + "。";
	}
	public String paymentInfo_TimeoutHandler(Integer id) {
		return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeoutHandler, id: " + id
				+ "\t" + "o(T_T)o~";
	}
	
	// ====== 服务熔断 =======
	@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
			@HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")// 失败率
            // 加起来就是在10s内的10次请求中如果失败超过6次进入服务熔断
	})
	public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
		if (id < 0) {
			throw new RuntimeException("id不能为负数。");
		}
		String serialNumber = IdUtil.simpleUUID();
		return Thread.currentThread().getName() + "\t调用成功，流水号：" + serialNumber;
	}
	public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
		return "id不能为负数，请稍后重试，/(ToT)/~~ id: " + id;
	}
	
}
