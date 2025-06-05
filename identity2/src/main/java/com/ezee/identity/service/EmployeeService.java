package com.ezee.identity.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ezee.identity.dto.Employee;
import com.ezee.identity.dto.LoginRequest;
import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.repository.EmployeeRepository;
import com.ezee.identity.security.SecurityValid;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

//	@Autowired
//	private LoginRequest loginRequest;

	@Autowired
	private CacheService cacheService;
	@Autowired
	private AuthService authService;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createEmployeeService(String inputToken, Employee employee) {
		int create = 0;
		try {
			if (authService.authTokenValid(inputToken) == true) {
//				if (SecurityValid.isAuthrized(inputToken, user.getRole())
//						&& loginRequest.getRole().equalsIgnoreCase("ADMIN")) {
				create = employeeRepository.createEmployee(employee);
//				} else {
//					logger.info("user not have a permission to access");
//					throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//				}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return create;

	}

	public int updateEmployeeService(String inputToken, String code, Employee employee) {
		int update = 0;
		try {
			if (authService.authTokenValid(inputToken) == true) {
//				if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//						&& (loginRequest.getRole().equalsIgnoreCase("ADMIN")
//								|| loginRequest.getRole().equalsIgnoreCase("STAFF"))) {
				Object employeeCache = cacheService.updateCache("identityCache", code, employee);
				if (employeeCache == null) {
					update = employeeRepository.updateEmployee(code, employee);
				}
//				} else {
//					throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//				}

			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (

		Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return update;
	}

	public Employee getEmployeeService(String inputToken, String Code) {
		Employee employee = null;
		try {
			if (authService.authTokenValid(inputToken) == true) {
//				if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//						&& (loginRequest.getRole().equalsIgnoreCase("ADMIN"))) {
				employee = cacheService.getCache("identityCache", Code, Employee.class);
				if (employee == null) {
					employee = employeeRepository.getEmployee(Code);
				}
				logger.info("fetch the record");
//				} else {
//					throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//				}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (

		Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return employee;
	}

	public List<Employee> fetchAllEmployeeService(String inputToken) {
		List<Employee> employeeList = new ArrayList<>();
		try {
			if (authService.authTokenValid(inputToken) == true) {
//				if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//						&& (loginRequest.getRole().equalsIgnoreCase("ADMIN"))) {
				employeeList = cacheService.getAllCache("identityCache", Employee.class);
				if (employeeList == null) {
					employeeList = employeeRepository.fetchAllEmployee();
				}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return employeeList;
	}
}
