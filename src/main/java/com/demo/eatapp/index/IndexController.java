package com.demo.eatapp.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

		static {
			System.out.println("Hit");
		}
		
	   @RequestMapping("/")
	   public String indexRoot() {
		   System.out.println("Hitting #1");
	      return "index";
	   }
	   
	   @RequestMapping("/index")
	   public String indexExplicit() {
		   System.out.println("Hitting #2");
	      return "index";
	   }
	
}
