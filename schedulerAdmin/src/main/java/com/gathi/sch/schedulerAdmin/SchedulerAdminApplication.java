package com.gathi.sch.schedulerAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SchedulerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerAdminApplication.class, args);
	}

}
