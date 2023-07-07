package controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import survey.*;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@GetMapping
	public ModelAndView form(Model model) {	// Model 클래스 객체를 사용하여 spring -> jsp로 데이터 전송
		List<Question> questions = createQuestions();
		ModelAndView mav = new ModelAndView();
		mav.addObject("questions", questions);
		mav.setViewName("survey/surveyForm");		// 전달할 뷰의 이름
		model.addAttribute("questions", questions);		// 뷰에 전달할 모델 데이터, jsp에서 ${questions}로 데이터 접근 가능
		return mav;
	}
	
	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?", Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발 도구는 무엇입니까?", Arrays.asList("Eclipse", "IntelliJ", "Sublime"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}
	
	@PostMapping
	public String submit(@ModelAttribute("ansData") AnsweredData data) {
		return "survey/submitted";
	}
}
