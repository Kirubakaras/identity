package com.ezee.identity.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ServiceException;

@Service
public class AuthService {
	@Autowired
	private CacheService cacheService;

	public boolean authTokenValid(String inputToken) {
		User user = cacheService.gettokenCache("tokenEhCache", inputToken, User.class);
		boolean valid = true;
		if (user != null) {
			valid = true;
		} else {
			throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
		}
		return valid;
	}

//	public static boolean isAuthrized(String inputToken, String requiredRole) {
//		LoginRequest loginRequest = new LoginRequest();
//		boolean authorized = false;
//		if (loginRequest.getAuthToken().equals(inputToken) && loginRequest.getRole().equals(requiredRole)) {
//			authorized = true;
//		}
//		return authorized;
//	}

}
