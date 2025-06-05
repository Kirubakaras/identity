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
import com.ezee.identity.dto.Supplier;
import com.ezee.identity.dto.User;
import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.exception.ServiceException;
import com.ezee.identity.repository.SupplierRepository;
import com.ezee.identity.security.SecurityValid;

@Service
public class SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;

//	@Autowired
//	private LoginRequest loginRequest;
//
//	@Autowired
//	private User user;

	@Autowired
	private AuthService authService;

	@Autowired
	private CacheService cacheService;

	private static final Logger logger = LogManager.getLogger("com.identity");

	public int createSupplierService(String inputToken, Supplier supplier) {
		int supplierCreated = 0;
		try {
			if (authService.authTokenValid(inputToken) == true) {

//					if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//							&& ((loginRequest.getRole().equalsIgnoreCase("ADMIN"))
//									|| (loginRequest.getRole().equalsIgnoreCase("SUPPLIER")))) {
				supplierCreated = supplierRepository.createSupplier(supplier);

//				} else {
//					throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//				}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}

		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return supplierCreated;
	}

	public Supplier getSupplierService(String inputToken, String supplierCode) {
		Supplier supplier = null;
		try {
			if (authService.authTokenValid(inputToken) == true) {
//					if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//							&& ((loginRequest.getRole().equalsIgnoreCase("ADMIN"))
//									|| (loginRequest.getRole().equalsIgnoreCase("SUPPLIER")))) {
				supplier = cacheService.getCache("identityCache", supplierCode, supplier.getClass());
				if (supplier == null) {
					supplier = supplierRepository.getSupplier(supplierCode);
				}
//					} else {
//						throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//					}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (

		ServiceException e) {
			logger.error("check the given record" + e.getMessage());
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return supplier;
	}

	public int updateSupplierService(String inputToken, String supplierCode, Supplier supplier) {
		int supplierUpdate = 0;
		try {
			if (authService.authTokenValid(inputToken) == true) {
//					if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//							&& ((loginRequest.getRole().equalsIgnoreCase("ADMIN"))
//									|| (loginRequest.getRole().equalsIgnoreCase("SUPPLIER")))) {
				Object supplierCache = cacheService.updateCache("identityCache", supplierCode, supplier);
				if (supplierCache == null) {
					supplierUpdate = supplierRepository.updateSupplier(supplierCode, supplier);
				}
//					} else {
//						throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//					}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (ServiceException e) {

			logger.error("check the given record" + e.getMessage());
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return supplierUpdate;
	}

	public List<Supplier> fetchAllService(String inputToken) {
		List<Supplier> supplierList = new ArrayList<>();
		try {

			if (authService.authTokenValid(inputToken) == true) {
//				if ((SecurityValid.isAuthrized(inputToken, user.getRole()))
//						&& ((loginRequest.getRole().equalsIgnoreCase("ADMIN"))
//								|| (loginRequest.getRole().equalsIgnoreCase("SUPPLIER")))) {
				supplierList = cacheService.getAllCache("identityCache", Supplier.class);
				if (supplierList == null) {
					supplierList = supplierRepository.fetchAllSupplier();
				}			
//				} else {
//					logger.info("dont have the permision to access");
//					throw new ServiceException(ErrorCode.INVALID_PERMISSION);
//				}
			} else {
				logger.info("user is not founded");
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}
		} catch (ServiceException e) {
			logger.error("check the given record" + e.getMessage());
		} catch (Exception e) {
			logger.error("check the given record" + e.getMessage());
		}
		return supplierList;
	}
}
