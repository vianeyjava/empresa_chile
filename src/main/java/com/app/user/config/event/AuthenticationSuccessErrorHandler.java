package com.app.user.config.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * AuthenticationSuccessErrorHandler.java para registrar evento en caso que el 
 * login haya sido exitoso o no.
 * 
 * @author Vianey Vargas Morales
 * @version 1.0
 *
 */
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		// TODO Auto-generated method stub
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		if(loggedInUser !=null) {
			UserDetails username = (UserDetails) loggedInUser.getPrincipal();
			System.out.println("Success Login: "+username);
			logger.info("Success Login ==>" +username);
		}
		
		//UserDetails user = (UserDetails)authentication.getPrincipal();
		//System.out.println("Success Login: "+user.getUsername());
		//logger.info("Success Login ==>" +user.getUsername());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		// TODO Auto-generated method stub
		System.out.println("Error Login: "+exception.getMessage());
		logger.error("Error en el Login ==>"+exception.getMessage());
	}

}
