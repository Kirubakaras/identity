package com.ezee.identity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.ezee.identity.dto.Employee;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/createemployee/{inputToken}")
	public ResponseEntity<?> createEmployeeController(@PathVariable String inputToken, @RequestBody Employee employee) {
		employeeService.createEmployeeService(inputToken, employee);
		return ResponseException.success("created successfully");
	}

	@PostMapping("/updateemployee/{inputToken}/{code}")
	public ResponseEntity<?> updateEmployeeController(@PathVariable String inputToken, @PathVariable String code,
			@RequestBody Employee employee) {
		employeeService.updateEmployeeService(inputToken, code, employee);
		return ResponseException.success("updated successfully");
	}

	@GetMapping("/getemployee/{inputToken}/{code}")
	public ResponseEntity<?> getEmployeeController(@PathVariable String inputToken, @PathVariable String code) {
		Employee employee = employeeService.getEmployeeService(inputToken, code);
		return ResponseException.success(employee);

	}

	@GetMapping("/fetchall/{inputToken}")
	public ResponseEntity<?> fetchAllEmployeeController(@PathVariable String inputToken) {
		List<Employee> employeeList = new ArrayList<>();
		employeeList = employeeService.fetchAllEmployeeService(inputToken);
		return ResponseException.success(employeeList);
	}
}
