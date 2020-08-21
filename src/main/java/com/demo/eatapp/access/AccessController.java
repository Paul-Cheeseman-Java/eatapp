package com.demo.eatapp.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishments.model.Establishments;
import com.demo.eatapp.user.dao.UserDAO;
import com.demo.eatapp.user.model.User;

@SessionAttributes("deviceSize")
@Controller
public class AccessController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

	
		@GetMapping("/")
		public String root() {
			return "home";
		}
	   
		@GetMapping("/home")
		public String homeRoot() {
			return "home";
		}
		

	   @GetMapping(value = "/logout")
		public String logout(HttpServletRequest request, HttpServletResponse response) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}

			return "redirect:/";

		}


		@GetMapping(value = "/login")
		    public String login(Model model, HttpServletRequest httpServletRequest ) {
				model.addAttribute("user", new User());
		        return "login";
		    }


		private String getLoggedInUserName() {
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			if (principal instanceof UserDetails)
				return ((UserDetails) principal).getUsername();

			return principal.toString();
		}

		
	    @GetMapping(value = "/register")
		public String doRegister(Model model) {
			model.addAttribute("user", new User());
	    	return "register";
		 }
	    
	    @PostMapping(value = "/register")
		public String recieveRegister(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
	    	
	    	if (bindingResult.hasErrors()) {
	    		return "register";
			}
	    	
	    	String requestedUserName = request.getParameter("username");
			
			if (userDAO.getUser(requestedUserName) == null) {
		    	String encodedPassword  = passwordEncoder.encode(request.getParameter("password"));

				user.setUsername(requestedUserName);
				user.setPassword(encodedPassword);
				
				userDAO.insertUser(user);
				model.addAttribute("username", requestedUserName);
		    	return "registerSuccess";
			}
			else {
				model.addAttribute("error", true);
		    	return "register";
			}
		 }

		
		
		
		
}
