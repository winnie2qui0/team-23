package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class UserController {
	
	 @GetMapping("/hello")
	    public String hello() {
	        return "hello"; // 要導入的html
	    } 

}
