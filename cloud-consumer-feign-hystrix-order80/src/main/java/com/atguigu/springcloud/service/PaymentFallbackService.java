package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

	@Override
	public String paymentInfo_OK(Integer id) {
		return "---PaymentFallbackService fall back - paymentInfo_OK, o(T_T)o";
	}

	@Override
	public String paymentInfo_Timeout(Integer id) {
		return "---PaymentFallbackService fall back - paymentInfo_Timeout, o(T_T)o";
	}

}
