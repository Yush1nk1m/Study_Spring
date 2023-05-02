package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller		// 스프링 MVC에서 컨트롤러로 사용함을 명시
public class HelloController {
	
	@GetMapping("/hello")	// HTTP 요청 메소드 중 GET 메소드에 대한 매핑을 설정, "/hello" 경로로 들어온 요청을 hello() 메소드로 처리
	public String hello(Model model,	// Model 파라미터는 컨트롤러의 처리 결과를 뷰에 전달할 때 사용
			@RequestParam(value = "name", required = false) String name) {	// HTTP 요청 파라미터의 값을 메소드의 파라미터로 전달할 때 사용, name 요청 파라미터의 값을 name 파라미터에 전달
		model.addAttribute("greeting", "안녕하세요, " + name);	// greeting이라는 모델 속성에 값을 설정
		return "hello";		// 컨트롤러의 처리 결과를 보여줄 뷰의 이름으로 "hello"를 사용
	}

}
