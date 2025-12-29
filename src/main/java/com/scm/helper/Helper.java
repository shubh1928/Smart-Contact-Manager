package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
	
	public static String getEmailOfLoggedInUser(Authentication authentication)
	{
		
//		AuthenticationPrincipal principal = (AuthenticationPrincipal)authentication.getPrincipal();
		
		if (authentication instanceof OAuth2AuthenticationToken) {
			
			var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
			
			var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			var oAuth2User = (OAuth2User)authentication.getPrincipal();
			
			String username="";
			
			if (clientId.equalsIgnoreCase("google")) 
			{

				username = oAuth2User.getAttribute("email").toString();
				
			} 
			else if(clientId.equalsIgnoreCase("github"))
			{

				username = oAuth2User.getAttribute("email") != null 
						? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@gmail.com";
				
			}
			else 
			{

			}
			
			return username;
			
		} else {

			return authentication.getName();

		}
	}

	public static String getLinkForEmailVerification(String emailToken)
	{
		
		String link = "http://localhost:8080/auth/verify-email?token=" + emailToken;
		
		return  link;
		
	}
	
}
