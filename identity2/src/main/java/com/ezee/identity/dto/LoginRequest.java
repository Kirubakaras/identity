package com.ezee.identity.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginRequest {
	private String authToken;
	private LocalDateTime dateTime;
	private String role;
}
