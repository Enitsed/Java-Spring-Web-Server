package kr.co.crewmate.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.crewmate.api.model.Test;
import kr.co.crewmate.api.service.TestService;
import kr.co.crewmate.web.annotation.UserAuthCheck;

@Controller
public class HelloController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
	
	@Autowired
	private TestService testService;


	// 기본 인덱스페이지
	@RequestMapping(value="/hello/hello.do", method=RequestMethod.GET)
	public ModelAndView hello(Model model) {
		LOGGER.debug("[debug] 값 : {}", "==============================hello");
		
		return new ModelAndView("hello/hello");
	}
	
	// ContentNegotiatingViewResolver 를 거치지 않고 뷰를 새로 생성하여 반환한다.**
	@RequestMapping(value="/hello/hello.json", method=RequestMethod.GET)
	public View helloJson(Model model) {
		LOGGER.debug("[debug] 값 : {}", "==============================TEST");
		model.addAttribute("Test", "Test");
		return new MappingJackson2JsonView();
	}
	
	// 어노테이션 UserAuthCheck 사용
	@UserAuthCheck({"test1", "test2"})
	@RequestMapping(value="/hello/authCheck.do", method=RequestMethod.GET)
	public ModelAndView authCheck(Model model) {
		LOGGER.debug("[debug] 값 : {}", "==============================TEST AuthCheck");
		
		return new ModelAndView("hello/hello");
	}
	
	// 예외 발생시 WebExceptionResolver로 이동
	@RequestMapping(value="/hello/exception", method=RequestMethod.GET)
	public ModelAndView exception(Model model)  {
		LOGGER.debug("[debug] 값1 : {}, 값2 : {}, 값3 : {}", 1, 2, 3); // 로그 사용법
		String exceptionTest = "test";
		if (StringUtils.equals("test", exceptionTest)) {
			throw new RuntimeException();
		}
		
		return new ModelAndView("hello/hello");
	}
	
	// DB CRUD
	@RequestMapping(value="/hello/getTestList", method=RequestMethod.GET)
	public ModelAndView getTestList(Model model) {
		// 데이터 조회
		LOGGER.debug("[debug] 값 : {}", "=============getTestList");
		List<Test> testList = this.testService.getTestList();
		
		model.addAttribute("getTestList", testList);
		return new ModelAndView("hello/getTestList");
	}
	
	@RequestMapping(value="/hello/getTest.do", method=RequestMethod.GET)
	public ModelAndView getTest(Model model, @RequestParam("index") int index) {
		// 데이터 조회
		LOGGER.debug("[debug] 값 : {}", "=============getTest");
		
		model.addAttribute("getTest", this.testService.getTest(index));
		return new ModelAndView("hello/getTest");
	}

	@RequestMapping(value="/hello/createTest.do", method=RequestMethod.POST)
	public ModelAndView createTest(Model model, @RequestParam("title") String title, @RequestParam("contents") String contents) {
		// 데이터 추가
		LOGGER.debug("[debug] 값 : {}", "=============createTest");
		this.testService.createTest(title, contents);
		
		return new ModelAndView("hello/createTest");
	}

	@RequestMapping(value="/hello/updateTest.do", method=RequestMethod.POST)
	public ModelAndView updateTest(Model model, @RequestParam("index") int index, @RequestParam("title") String title, @RequestParam("contents") String contents) {
		// 데이터 수정
		LOGGER.debug("[debug] 값 : {}", "=============updateTest");
		this.testService.updateTest(index, title, contents);
		
		model.addAttribute("testObject", this.testService.getTest(index));
		return new ModelAndView("hello/updateTest");
	}
	
}