package com.atguigu.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Gloabl_FallbackMethod")
public class OrderHystrixController {
	@Resource
	private PaymentHystrixService paymentHystrixService;
	
	@GetMapping("/consumer/payment/hystrix/ok/{id}")
	public String paymentInfo_OK(@PathVariable("id") Integer id) {
		String result = paymentHystrixService.paymentInfo_OK(id);
		return result;
	}
	
	@GetMapping("/consumer/payment/hystrix/timeout/{id}")
//	@HystrixCommand(fallbackMethod = "paymentTimeoutFallbackMethod", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//	})
	@HystrixCommand
	public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
		String result = paymentHystrixService.paymentInfo_Timeout(id);
		return result;
	}
	public String paymentTimeoutFallbackMethod(@PathVariable("id") Integer id) {
		return "我说消费者80，对方支付系统繁忙请自己运行出错请检查自己，o(T_T)o";
	}
	
	// 下面是全局fallback方法
	public String payment_Gloabl_FallbackMethod() {
		return "Global异常处理信息，请稍后再试，/(ToT)/~~";
	}
}
