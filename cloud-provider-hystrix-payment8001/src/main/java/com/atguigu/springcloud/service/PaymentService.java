package com.atguigu.springcloud.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	public String paymentInfo_OK(Integer id) {
		return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id: " + id
				+ "\t" + "O(n_n)哈哈~";
	}
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
}
