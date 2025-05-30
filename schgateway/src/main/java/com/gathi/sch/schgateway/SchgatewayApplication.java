package com.gathi.sch.schgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SchgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchgatewayApplication.class, args);
	}

}
