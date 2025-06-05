package com.ezee.identity.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.identity.config.JdbcConfig;
import com.ezee.identity.dto.LogAudit;
import com.ezee.identity.dto.User;

import lombok.Cleanup;

@Repository
public class LogAuditRepository {

	@Autowired
	private JdbcConfig jdbcConfig;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int registerLogAudit(User user) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "{call SP_LOG_AUDIT(?,?,?)}";
			@Cleanup
			CallableStatement statement = connection.prepareCall(query);
			statement.setInt(1, user.getEmployeeId());
			statement.setString(2, LocalDateTime.now().toString());
			statement.setString(3, LocalDateTime.now().toString());
			executeUpdate = statement.executeUpdate();
			logger.info("login can be created");
		} catch (Exception e) {
			logger.error("Invalid record can be shown" + e.getMessage());
		}
		return executeUpdate;
	}

	public List<LogAudit> fetchAll() {
		List<LogAudit> listLogAudit = new ArrayList<>();
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "select code,login,logout from log_audit";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				LogAudit logAudit = new LogAudit();
				logAudit.setEmployeeCode(resultSet.getString("code"));
				logAudit.setLogin(resultSet.getString("login"));
				logAudit.setLogout(resultSet.getString("logout"));
				listLogAudit.add(logAudit);
			}
			logger.info("fetch all record");

		} catch (Exception e) {
			logger.info("fetch all record " + e.getMessage());
		}
		return listLogAudit;
	}

}
