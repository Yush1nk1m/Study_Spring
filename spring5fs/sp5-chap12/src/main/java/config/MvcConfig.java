package config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/main").setViewName("main");	// "/main" 경로로의 요청에 대한 뷰를 "view/main.jsp"로 설정
		registry.addViewController("/").setViewName("main");		// "/" 경로로의 요청에 대한 뷰를 "view/main.jsp"로 설정
	}
	
	@Bean	// <spring:message> 태그 사용 시 내부적으로 MessageSource#getMessage() 메소드가 실행되어 메시지를 구함
	public MessageSource messageSource() {	// 주의: Bean ID가 messageSource가 아니면 작동하지 않는다
		ResourceBundleMessageSource ms =
				new ResourceBundleMessageSource();	// MessageSource 인터페이스의 구현체: 자바의 리소스 번들 사용하므로 프로퍼티 파일이 클래스 패스(src/main/resources)에 위치해야 함
		ms.setBasenames("message.label");	// message 패키지에 속한 label 프로퍼티 파일로부터 메시지를 읽어온다고 설정, 가변 인자이므로 목록을 전달할 수도 있다
		ms.setDefaultEncoding("UTF-8");		// 기본 인코딩 방식을 UTF-8로 설정
		return ms;
	}

}
