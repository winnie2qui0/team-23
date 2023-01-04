package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.servlet.ModelAndView;

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class UserController {
	
	 @GetMapping("/greeting")
	    public String hello(Model model) {
		 
		 model.addAttribute("greeting", new Greeting());
	        return "greeting"; 
	    }
	 
	 @PostMapping("/greeting")
	  public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
	    model.addAttribute("greeting", greeting);
	    return "result";
	  }
	 
	 @GetMapping("/index")
	    public String getIndex(@ModelAttribute UserForm userForm, Model model, FakeData fakedata) {
		 
		 model.addAttribute("userForm", new UserForm());
		 model.addAttribute("fakedata", new FakeData());
		 System.out.println("this is get");
	        return "index"; 
	    }
	 
//	 @PostMapping(path = "/index")
//	  public String indexForm(@ModelAttribute UserForm userForm, Model model, FakeData fakedata) throws IOException, InterruptedException, ExecutionException, TimeoutException {
//	    model.addAttribute("userForm", userForm);
//	    model.addAttribute("fakedata", fakedata);
//	    //fakedata.setResult(userForm.getContent());
//	    System.out.println(userForm.getContent());
//	    return "<div id=\"result\" hx-swap-oob=\"true\">Hello</div>\n";
//	  }
	 
//	 public class HtmlController {
//	     @PostMapping(value = "/index", produces = MediaType.TEXT_HTML_VALUE)
//	     @ResponseBody
//	     public String welcomeAsHTML() {
//	    	 return "<div id=\"result\" hx-swap-oob=\"true\">danny</div>\n";
//	     }
//	 }
	 
//	 @PostMapping(path = "/index")
//	 public Flux<Rendering> test() {
//	 	return Flux.just(
//	 			Rendering.view("index").modelAttribute("id", "result")
//	 				.modelAttribute("value", "Danny hello").build());
//	 }
	 
//	 @PostMapping(path = "/index")
//	 public String test() {
//	 	return "<div id=\"result\" hx-swap-oob=\"true\">Hello</div>\n";
//	 }
	 
	 @PostMapping("/index")
	 public ModelAndView thymeleafView(@ModelAttribute UserForm userForm, Model model, FakeData fakedata) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		 ModelAndView view = new ModelAndView("index");
		 fakedata.setResult(userForm.getContent());
		 view.addObject("number", 1234);
//		 view.addObject("result", 1234);
		 view.addObject("result", fakedata.getResult());
//	     model.put("number", 1234);
//	     model.put("message", "Hello from Spring MVC");
		 System.out.println("this is Post");
	     return view;
	 }

	 
	 

}
