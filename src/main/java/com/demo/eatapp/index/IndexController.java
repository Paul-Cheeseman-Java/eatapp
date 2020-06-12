package com.demo.eatapp.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	   @RequestMapping("/")
	   public String indexRoot() {
	      return "index";
	   }
	   
	   @RequestMapping("/index")
	   public String indexExplicit() {
	      return "index";
	   }
	
	   @RequestMapping("/homepage")
	   public String homepage() {
	      return "homepage";
	   }
	   
	   
}
