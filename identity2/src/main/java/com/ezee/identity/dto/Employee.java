package com.ezee.identity.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Employee {
	private int employeeId;
	private String employeeCode;
	private String employeeName;
	private String employeeEmail;
	private String employeePhoneNum;
	private String employeeAddress;
	private Date employeeJoinDate;
	private double employeeSalary;
	private int activeflag;
	private Date updateAt;
	private String updateBy;
}
