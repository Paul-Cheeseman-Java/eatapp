package com.demo.eatapp.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class IoginController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

	
	   @RequestMapping("/")
	   public String indexRoot() {
	      return "index";
	   }
	   
	   @RequestMapping("/index")
	   public String indexExplicit() {
	      return "index";
	   }
	

	   
}
