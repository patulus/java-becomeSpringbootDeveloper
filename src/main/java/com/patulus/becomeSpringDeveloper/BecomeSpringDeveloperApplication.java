package com.patulus.becomeSpringDeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 엔티티 객체가 생성이 되거나 변경이 되었을 때 자동으로 시간 값 등록
@SpringBootApplication
public class BecomeSpringDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BecomeSpringDeveloperApplication.class, args);
	}

}
