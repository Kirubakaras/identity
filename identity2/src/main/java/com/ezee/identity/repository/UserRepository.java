package com.ezee.identity.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.identity.config.JdbcConfig;
import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.util.TokenGenerator;

import ch.qos.logback.classic.spi.STEUtil;
import lombok.Cleanup;

@Repository
public class UserRepository {
	@Autowired
	private JdbcConfig jdbcConfig;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createUser(User user) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "insert into user (employee_id,usercode,username,password,role,activeflag,updateAt,updateBy)values(?,?,?,?,?,?,?,?)";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, user.getEmployeeId());
			statement.setString(2, TokenGenerator.generateCode(user.getUserCode()));
			statement.setString(3, user.getUserName());
			statement.setString(4, user.getPassWord());
			statement.setString(5, user.getRole());
			statement.setInt(6, user.getActiveFlag());
			statement.setDate(7, user.getUpdateAt());
			statement.setString(8, user.getUpdateBy());
			executeUpdate = statement.executeUpdate();
			logger.info("creted a user");
		} catch (Exception e) {
			logger.error("already record has been inserted");
		}
		return executeUpdate;
	}

	public User loginUser(User user) {
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connetion = dataSource.getConnection();

			String query = "select usercode,username,role,activeflag from user where username=? and password=? and activeflag=1";
			PreparedStatement statement = connetion.prepareStatement(query);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassWord());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setUserCode(resultSet.getString("usercode"));
				user.setUserName(resultSet.getString("username"));
				user.setRole(resultSet.getString("role"));
				user.setActiveFlag(resultSet.getInt("activeflag"));
			} else {
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
			logger.info("fetched the record");

		} catch (Exception e) {
			logger.error("give the correct username and password" + e.getMessage());
		}
		return user;
	}

	public int updateUser(User user) {
		int executeupdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection conection = dataSource.getConnection();

			String query = "update user set username=?,password=?,role=?,activeflag=?,updateAt=?,updateBy=? where usercode=? and activeflag=1";
			@Cleanup
			PreparedStatement statement = conection.prepareStatement(query);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassWord());
			statement.setString(3, user.getRole());
			statement.setInt(4, user.getActiveFlag());
			statement.setDate(5, user.getUpdateAt());
			statement.setString(6, user.getUpdateBy());
			statement.setString(7, user.getUserCode());
			executeupdate = statement.executeUpdate();
			logger.info("update user record");
		} catch (Exception e) {
			logger.error("enter the corrcet user record" + e.getMessage());
		}
		return executeupdate;
	}

}
