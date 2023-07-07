package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.*;
import spring.MemberRegisterService;

@Configuration
public class ControllerConfig {		// 프로젝트의 컨트롤러들의 정보를 담고 있는 클래스
	
	@Autowired	// 의존 주입 설정
	private MemberRegisterService memberRegSvc;
	
	@Bean
	public RegisterController registerController() {	// 회원가입 과정을 처리하는 RegisterController 클래스의 빈
		RegisterController controller = new RegisterController();
		controller.setMemberRegisterService(memberRegSvc);	// 의존 주입에 의해 빈 생성 시 자동으로 주입됨
		return controller;
	}
	
	@Bean
	public SurveyController surveyController() {
		return new SurveyController();
	}
	
}
