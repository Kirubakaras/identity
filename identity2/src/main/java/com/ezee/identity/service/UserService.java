package com.ezee.identity.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezee.identity.dto.LoginRequest;

import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.repository.LogAuditRepository;
import com.ezee.identity.repository.UserRepository;
import com.ezee.identity.security.SecurityValid;
import com.ezee.identity.util.TokenGenerator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LogAuditRepository logAuditRepository;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private AuthService authService;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createUserServie(User user) {
		int created = 0;
		try {
			created = userRepository.createUser(user);
			logger.info("successfully register");

		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return created;
	}

	public User loginUserService(User user) {
		try {
			user = userRepository.loginUser(user);
			if (user != null) {
//				LoginRequest request = new LoginRequest();
				String token = TokenGenerator.authTokenGenerator();
//				request.setAuthToken(token);
//				request.setDateTime(LocalDateTime.now());
//				request.setRole(user.getRole());
				logAuditRepository.registerLogAudit(user);
				cacheService.gettokenCache("tokenEhCache", token, user.getClass());
				logger.info("successfully login");
			}
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return user;
	}

	public int updateUserServise(String inputToken, User user) {
		int updated = 0;
		try {
			if (authService.authTokenValid(inputToken) == true) {

//				if (SecurityValid.isAuthrized(inputToken, user.getRole())
//						&& request.getRole().equalsIgnoreCase("ADMIN")) {
				Object userCache = cacheService.updateCache("identityCache", user.getUserCode(), user);
				if (userCache == null) {
					updated = userRepository.updateUser(user);
				}
				logger.info("update user recored");
//				}
			} else {
				logger.info("dont have the permision to access");
			}
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return updated;
	}
}
