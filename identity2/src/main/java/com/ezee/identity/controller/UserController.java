package com.ezee.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> createUserController(@RequestBody User user) {
		userService.createUserServie(user);
		return ResponseException.success("Created successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginuser(@RequestBody User user) {
		user = userService.loginUserService(user);
		return ResponseException.success(user);
	}

	@PostMapping("/updateEmployee/{code}")
	public ResponseEntity<?> updateEmployeeController(@PathVariable String code, @RequestBody User user) {
		userService.updateUserServise(code, user);
		return ResponseException.success("updated successfully");
	}

}
