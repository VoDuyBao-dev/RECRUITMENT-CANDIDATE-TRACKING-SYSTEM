package com.example.RecruitmentCandidateTracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // <--- Thêm dòng này để kích hoạt tự động điền ngày tháng
public class RecruitmentCandidateTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentCandidateTrackingApplication.class, args);
	}

}
