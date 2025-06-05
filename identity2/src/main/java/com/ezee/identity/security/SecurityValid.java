package com.ezee.identity.security;

import java.time.Duration;
import java.time.LocalDateTime;

import com.ezee.identity.dto.LoginRequest;
import com.ezee.identity.dto.User;

public class SecurityValid {
	public static boolean isTokenvalidate(String inputToken) {
		LoginRequest loginRequest = new LoginRequest();
		boolean tokenValid = false;

		if ((loginRequest.getAuthToken().equals(inputToken))
				&& (Duration.between(loginRequest.getDateTime(), LocalDateTime.now()).toMinutes() < 30)) {
			tokenValid = true;
//			if (loginRequest.getRole() == null) {
//				tokenValid = false;
//			}
		}
		return tokenValid;
	}

	public static boolean isAuthrized(String inputToken, String requiredRole) {
		LoginRequest loginRequest = new LoginRequest();
		boolean authorized = false;
		if (loginRequest.getAuthToken().equals(inputToken) && loginRequest.getRole().equals(requiredRole)) {
			authorized = true;
		}
		return authorized;
	}
	

}
