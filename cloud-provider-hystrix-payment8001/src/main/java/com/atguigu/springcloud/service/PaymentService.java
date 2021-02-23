package com.atguigu.springcloud.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

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
}
