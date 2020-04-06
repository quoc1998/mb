package com.backend.bank;

import com.backend.bank.common.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BankApplication {

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		System.out.println("Date in UTC: " + new Date().toString());
	}
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}


}
