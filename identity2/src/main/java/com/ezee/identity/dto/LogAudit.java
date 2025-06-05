package com.ezee.identity.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LogAudit {
	private int logId;
	private String employeeCode;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String login;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String logout;
}
