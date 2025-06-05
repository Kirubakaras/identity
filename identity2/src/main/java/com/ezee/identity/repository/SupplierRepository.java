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
import com.ezee.identity.dto.Supplier;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.util.TokenGenerator;

import lombok.Cleanup;

@Repository
public class SupplierRepository {

	@Autowired
	private JdbcConfig jdbcConfig;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createSupplier(Supplier supplier) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();

			String query = "insert into supplier(supplier_code,supplier_name,supplier_email,supplier_phonenum,supplier_address,activeflag,update_at,update_by)values (?,?,?,?,?,?,?,?)";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, TokenGenerator.generateCode(supplier.getSupplierCode()));
			statement.setString(2, supplier.getSupplierName());
			statement.setString(3, supplier.getSupplierEmail());
			statement.setString(4, supplier.getSupplierPhoneNum());
			statement.setString(5, supplier.getSupplierAddress());
			statement.setInt(6, supplier.getActiveflag());
			statement.setDate(7, supplier.getUpdateAt());
			statement.setString(8, supplier.getUpdateBy());
			logger.info("inseerted successfully ");
		} catch (Exception e) {
			logger.error("already record has been inserted " + e.getMessage());
			throw new ServiceException(ErrorCode.ALREADY_RECORED_EXISTS);
		}
		return executeUpdate;
	}

	public int updateSupplier(String suppliercode, Supplier supplier) {
		int executeUpdate = 0;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connnection = dataSource.getConnection();
			String query = "update supplier set supplier_name=?,supplier_email=?,supplier_phonenum=?,supplier_address=?, activeflag=?,update_at=?,update_by=? where supplier_code=? and activeflag=1";
			@Cleanup
			PreparedStatement statement = connnection.prepareStatement(query);
			statement.setString(1, supplier.getSupplierName());
			statement.setString(2, supplier.getSupplierEmail());
			statement.setString(3, supplier.getSupplierPhoneNum());
			statement.setString(4, supplier.getSupplierAddress());
			statement.setString(5, supplier.getSupplierAddress());
			statement.setInt(6, supplier.getActiveflag());
			statement.setDate(7, supplier.getUpdateAt());
			statement.setString(8, supplier.getUpdateBy());

			executeUpdate = statement.executeUpdate();
			logger.info("update employee record");
		} catch (Exception e) {
			logger.error("enter the corrcet employee record" + e.getMessage());
			throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
		}
		return executeUpdate;
	}

	public Supplier getSupplier(String supplierCode) {
		Supplier supplier = null;
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "select supplier_code,supplier_name,supplier_email,supplier_phonenum,supplier_address,activeflag,update_at,update_by from supplier where supplier_code=? and activeflag=1";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, supplierCode);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				supplier = new Supplier();
				supplier.setSupplierCode(resultSet.getString("supplier_code"));
				supplier.setSupplierName(resultSet.getString("supplier_name"));
				supplier.setSupplierEmail(resultSet.getString("supplier_email"));
				supplier.setSupplierPhoneNum(resultSet.getString("supplier_phonenum"));
				supplier.setSupplierAddress(resultSet.getString("supplier_address"));
				supplier.setActiveflag(resultSet.getInt("activeflag"));
				supplier.setUpdateAt(resultSet.getDate("update_at"));
				supplier.setUpdateBy(resultSet.getString("update_by"));

			}
			logger.info("fetched the record");
		} catch (Exception e) {
			logger.error("enter the corrcet record" + e.getMessage());
			throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
		}
		return supplier;
	}

	public List<Supplier> fetchAllSupplier() {
		List<Supplier> supplierList = new ArrayList<>();
		try {
			DataSource dataSource = jdbcConfig.getDataSource();
			@Cleanup
			Connection connection = dataSource.getConnection();
			String query = "select supplier_code,supplier_name,supplier_email,supplier_phonenum,supplier_address,activeflag,update_at,update_by from supplier where activeflag=1";
			@Cleanup
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Supplier supplier = new Supplier();
				supplier.setSupplierCode(resultSet.getString("supplier_code"));
				supplier.setSupplierName(resultSet.getString("supplier_name"));
				supplier.setSupplierEmail(resultSet.getString("supplier_email"));
				supplier.setSupplierPhoneNum(resultSet.getString("supplier_phonenum"));
				supplier.setSupplierAddress(resultSet.getString("supplier_address"));
				supplier.setActiveflag(resultSet.getInt("activeflag"));
				supplier.setUpdateAt(resultSet.getDate("update_at"));
				supplier.setUpdateBy(resultSet.getString("update_by"));
				supplierList.add(supplier);
			}
			logger.info("fetched the record");
		} catch (Exception e) {
			logger.error("empty recored" + e.getMessage());
		}
		return supplierList;
	}
}
