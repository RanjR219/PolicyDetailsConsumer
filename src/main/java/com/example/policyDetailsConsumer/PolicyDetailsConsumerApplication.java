package com.example.policyDetailsConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class PolicyDetailsConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyDetailsConsumerApplication.class, args);
	}

}
