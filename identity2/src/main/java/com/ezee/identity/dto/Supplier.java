package com.ezee.identity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Supplier {
	private int supplierId;
	private String supplierCode;
	private String supplierName;
	private String supplierEmail;
	private String supplierPhoneNum;
	private String supplierAddress;
	private int activeflag;
	private Date updateAt;
	private String updateBy;
}
