package com.ezee.identity.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User {

	private int UserId;
	private int employeeId;
	private String userCode;
	private String userName;
	private String passWord;
	private String role;
	private int activeFlag;
	private Date updateAt;
	private String updateBy;

}
