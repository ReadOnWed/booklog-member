package com.booklog.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


//아직 인증 단계 개발 전이므로 시큐리티 로그인 서비스를 제어한다.
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

}
