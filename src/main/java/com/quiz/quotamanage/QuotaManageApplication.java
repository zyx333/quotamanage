package com.quiz.quotamanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = "com.quiz.quotamanage.mapper")
public class QuotaManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotaManageApplication.class, args);
	}

}
