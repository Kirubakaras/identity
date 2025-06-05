package com.ezee.identity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezee.identity.dto.Supplier;
import com.ezee.identity.exception.ResponseException;
import com.ezee.identity.service.SupplierService;

@RestController
public class SupplierController {
	@Autowired
	private SupplierService supplierService;

	@PostMapping("/createsupplier/{token}")
	public ResponseEntity<?> createSupplierController(@PathVariable String inputToken, @RequestBody Supplier supplier) {
		supplierService.createSupplierService(inputToken, supplier);
		return ResponseException.success(supplier);
	}

	@GetMapping("/getsupplier/{token}/{suppliercode}")
	public ResponseEntity<?> getSupplierController(@PathVariable String inputToken, @PathVariable String supplierCode) {
		Supplier supplier = supplierService.getSupplierService(inputToken, supplierCode);
		return ResponseException.success(supplier);
	}

	@PostMapping("/updatesupplier/{token}/{suppliercode}")
	public ResponseEntity<?> updateSupplierController(@PathVariable String inputToken,
			@PathVariable String supplierCode, @RequestBody Supplier supplier) {
		supplierService.updateSupplierService(inputToken, supplierCode, supplier);
		return ResponseException.success(supplier);
	}

	@GetMapping("/fetchallsupplier/{token}")
	public ResponseEntity<?> fetchAllSupplierController(@PathVariable String inputToken) {
		List<Supplier> supplierList=new ArrayList<>();
		supplierList= supplierService.fetchAllService(inputToken);
		return ResponseException.success(supplierList);
	}
}
