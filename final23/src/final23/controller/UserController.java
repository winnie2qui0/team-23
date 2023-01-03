package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class UserController {
	
	 @GetMapping("/index")
	    public String hello() {
	        return "index"; // 要導入的html
	    } 

}
