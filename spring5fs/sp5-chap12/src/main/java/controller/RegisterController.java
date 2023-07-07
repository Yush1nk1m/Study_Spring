package controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.PasswordsNotEqualException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller		// 컨트롤러 클래스로 지정
@RequestMapping("/register")	// "/register" 경로로 들어온 요청을 mapping (공통 경로)
public class RegisterController {	// 회원가입 과정을 처리하는 컨트롤러 클래스
	
	private MemberRegisterService memberRegisterService;	// 회원가입을 위해 멤버 변수로 MemberRegisterService의 객체 생성
	
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {		// MemberRegisterService 클래스 멤버 객체에 대한 setter, 자동 주입 설정은 ControllerConfig.java에서 이루어짐
		this.memberRegisterService = memberRegisterService;
	}
	
	@RequestMapping("/step1")		// "/register/step1" 경로로 들어온 요청을 mapping
	public String handleStep1() {	// "/register/step1" 경로로 들어온 요청을 처리하는 메소드
		return "register/step1";	// 약관 내용을 보여줄 뷰의 이름을 반환(view/register/step1.jsp를 실행)
	}
	
	// PostMapping 방식은 POST 방식의 요청만 처리하므로 주소 창에 직접 주소를 입력하는 GET 방식에 의한 요청은 차단한다.
	@PostMapping("/step2")			// "/register/step2" 경로로 들어온 요청을 mapping
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,	// value(spring)<->name(jsp), defaultValue: 아무 값도 전달되지 않았을 시 가지는 값, required(spring)<->required(html)
			Model model) {
		if (!agree)		// jsp의 name="agree"인 요소로부터 서버에 전달된 값이 false 또는 전달되지 않아 defaultValue(false)인 경우
			return "register/step1";	// register/step1(약관 내용을 보여줄 뷰의 이름) 반환, view/register/step1.jsp 실행
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";	// jsp의 name="agree"인 요소로부터 서버에 전달된 값이 true이면 입력 폼을 보여줄 뷰의 이름을 반환(view/register/step2.jsp를 실행)
	}

	@PostMapping("/step3")			// "/register/step3" 경로로 들어온 요청을 mapping
	public String handleStep3(RegisterRequest regReq) {		// RegisterRequest 클래스의 객체(registerRequest)를 커맨드 객체로 지정, setter 메소드를 이용해 요청 파라미터의 값 전달
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException e) {
			return "register/step2";
		} catch (PasswordsNotEqualException e) {
			return "register/step2";
		}
	}
	
	@GetMapping("/register/step2")	// GET 방식으로 "/register/step2" 경로에 들어온 요청을 mapping
	public String handleStep2Get() {
		return "redirect:/register/step1";	// "/register/step1"에 대한 요청으로 리다이렉트
	}

}
