package com.demo.eatapp.access;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private DataSource dataSource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/search","/list").access("hasRole('USER')")
		.antMatchers("/search/","/list/").access("hasRole('USER')")
		.and()
		.formLogin()

			.loginPage("/login")
			.defaultSuccessUrl("/search", true)
		.permitAll();
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
        auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("SELECT username, password, enabled from users where username = ?")
        .authoritiesByUsernameQuery("SELECT username, authority, enabled from users where username = ?");
	}

	 @Bean
	 public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	 }

}

