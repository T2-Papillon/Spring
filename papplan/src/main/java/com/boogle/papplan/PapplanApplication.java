package com.boogle.papplan;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
//@EnableSwagger2
public class PapplanApplication {

	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PapplanApplication.class, args);
	}
}
