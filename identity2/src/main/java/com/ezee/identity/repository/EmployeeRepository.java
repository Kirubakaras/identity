package com.ezee.identity.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.identity.config.JdbcConfig;
import com.ezee.identity.dto.Employee;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.util.TokenGenerator;

import lombok.Cleanup;

@Repository
public class EmployeeRepository {

	@Autowired
	private JdbcConfig jdbcConfig;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createEmployee(Employee employee) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();

			String query = "insert into employee(employee_code, employee_name, employee_email, employee_phonenum, employee_address, employee_joindate, emplooyee_salary, activeflag, update_at, update_by) values (?,?,?,?,?,?,?,?,?,?)";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, TokenGenerator.generateCode(employee.getEmployeeCode()));
			statement.setString(2, employee.getEmployeeName());
			statement.setString(3, employee.getEmployeeEmail());
			statement.setString(4, employee.getEmployeePhoneNum());
			statement.setString(5, employee.getEmployeeAddress());
			statement.setDate(6, employee.getEmployeeJoinDate());
			statement.setDouble(7, employee.getEmployeeSalary());
			statement.setInt(8, employee.getActiveflag());
			statement.setDate(9, employee.getUpdateAt());
			statement.setString(10, employee.getUpdateBy());
			executeUpdate = statement.executeUpdate();
			logger.info("inseerted successfully ");
		} catch (Exception e) {
			logger.error("already record has been inserted " + e.getMessage());
		}
		return executeUpdate;
	}

	public int updateEmployee(String code, Employee employee) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connnection = dataSource.getConnection();
			String query = "update employee set employee_name=?, employee_email=?, employee_phonenum=?, employee_address=?, employee_joindate=?, employee_salary=?, activeflag=?, update_at=?, update_by=? where employee_code=? and activeflag=1";
			@Cleanup
			PreparedStatement statement = connnection.prepareStatement(query);
			statement.setString(1, employee.getEmployeeName());
			statement.setString(2, employee.getEmployeeEmail());
			statement.setString(3, employee.getEmployeePhoneNum());
			statement.setString(4, employee.getEmployeeAddress());
			statement.setDate(5, employee.getEmployeeJoinDate());
			statement.setDouble(6, employee.getEmployeeSalary());
			statement.setInt(7, employee.getActiveflag());
			statement.setDate(8, employee.getUpdateAt());
			statement.setString(9, employee.getUpdateBy());
			statement.setString(10, code);
			executeUpdate = statement.executeUpdate();
			logger.info("update employee record");
		} catch (Exception e) {
			logger.error("enter the corrcet employee record" + e.getMessage());
		}
		return executeUpdate;
	}

	public Employee getEmployee(String code) {
		Employee employee = null;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "select employee_code, employee_name, employee_email, employee_phonenum, employee_address, employee_joindate, employee_salary, activeflag, update_at, update_by from employee where employee_code=? and activeflag=1";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, code);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employee = new Employee();
				employee.setEmployeeCode(resultSet.getString("employee_code"));
				employee.setEmployeeName(resultSet.getString("employee_name"));
				employee.setEmployeeEmail(resultSet.getString("employee_email"));
				employee.setEmployeePhoneNum(resultSet.getString("employee_phonenum"));
				employee.setEmployeeAddress(resultSet.getString("employee_address"));
				employee.setEmployeeJoinDate(resultSet.getDate("employee_joinDate"));
				employee.setEmployeeSalary(resultSet.getDouble("employee_salary"));
				employee.setActiveflag(resultSet.getInt("activeflag"));
				employee.setUpdateAt(resultSet.getDate("update_at"));
				employee.setUpdateBy(resultSet.getString("update_by"));
			}
			logger.info("fetched the record");
		} catch (Exception e) {
			logger.error("enter the corrcet record" + e.getMessage());
		}
		return employee;
	}

	public List<Employee> fetchAllEmployee() {
		List<Employee> employeeList = new ArrayList<>();
		try {
			DataSource dateSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dateSource.getConnection();
			String query = "select employee_code, employee_name, employee_email, employee_phonenum, employee_address, employee_joindate, emplooyee_salary, activeflag, update_at, update_by from employee where activeflag=1";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setEmployeeCode(resultSet.getString("employee_code"));
				employee.setEmployeeName(resultSet.getString("employee_name"));
				employee.setEmployeeEmail(resultSet.getString("employee_email"));
				employee.setEmployeePhoneNum(resultSet.getString("employee_Phonenum"));
				employee.setEmployeeAddress(resultSet.getString("employee_address"));
				employee.setEmployeeJoinDate(resultSet.getDate("employee_joinDate"));
				employee.setEmployeeSalary(resultSet.getDouble("employee_salary"));
				employee.setActiveflag(resultSet.getInt("activeflag"));
				employee.setUpdateAt(resultSet.getDate("update_at"));
				employee.setUpdateBy(resultSet.getString("update_by"));
				employeeList.add(employee);
			}
			logger.info("fetch all records");
		} catch (Exception e) {
			logger.error("empty record" + e.getMessage());
		}
		return employeeList;
	}
}
