package com.scm.config;

import com.scm.controller.PageController;
import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {
	
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("admin").password("admin123").build();
//		
//		var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
//		
//		return inMemoryUserDetailsManager;
//	}
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private OAuthAuthenticationSuccessHandler handler;
	
	@Autowired
	private AuthFailureHandler authFailureHandler;
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
	    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	    
	    daoAuthenticationProvider.setUserDetailsService(userDetailService);
	    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	    
	    return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		
		//url's are configured based on public and private
		httpSecurity.authorizeHttpRequests(authorize -> {
//			authorize.requestMatchers("/home","/about","/services","/contact","/login","/register").permitAll();
			
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		
		//form default login
		//if we want to change anything related to form login 
		httpSecurity.formLogin(formLogin -> {
			
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/profile");
//			formLogin.failureForwardUrl("/login?error=true");
		  //formLogin.defaultSuccessUrl("/home");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
			
			formLogin.failureHandler(authFailureHandler);
			
		});
		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		httpSecurity.logout(logoutForm -> {
			
			logoutForm.logoutUrl("/do-logout");
			logoutForm.logoutSuccessUrl("/login?logout=true");
			
		});
		
		//oauth2 configuration
		httpSecurity.oauth2Login(oauth -> {
			oauth.loginPage("/login");
			oauth.successHandler(handler);
		});
		
		return httpSecurity.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
