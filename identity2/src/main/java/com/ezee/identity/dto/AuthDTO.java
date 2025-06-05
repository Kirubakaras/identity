package com.ezee.identity.dto;

import lombok.Data;

@Data
public class AuthDTO {
	private String authToken;
	private String code;
	private String role;
	
}
