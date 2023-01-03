package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
	 
	 @PostMapping("/index")
	  public String indexForm(@ModelAttribute UserForm userForm, Model model, FakeData fakedata) {
	    model.addAttribute("userForm", userForm);
	    model.addAttribute("fakedata", fakedata);
	    System.out.println(userForm.getContent());
	    return "index";
	  }
	 
	 

}
