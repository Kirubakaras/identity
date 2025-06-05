package com.ezee.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/getauth")
	public boolean authTokenValid(@RequestHeader String inputToken) {
		return authService.authTokenValid(inputToken);
	}
}
