package com.project.kall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class KallApplication {

	public static void main(String[] args) {
		SpringApplication.run(KallApplication.class, args);
	}

}
