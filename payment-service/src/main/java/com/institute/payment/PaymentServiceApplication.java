package com.institute.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableFeignClients
@PropertySource(value = "classpath:PaymentApplicationConfig.yaml", factory = com.institute.payment.config.YamlPropertySourceFactory.class)
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
