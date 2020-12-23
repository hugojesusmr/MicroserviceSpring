package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Customer;
import com.example.entity.Region;

import com.example.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerRest {
	
	@Autowired
	CustomerService customerService;

	// --------------------------    Retrieve All Customers    ---------------------------//
	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId", required = false) Long regionId){
		List<Customer> customers = new ArrayList<>();
		
		//si no se envia una region se listan
		if (regionId == null) {
			customers = customerService.findCustomerAll();
			
			//si es null no existe contenido
			if (customers.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			
		}else {
			Region region = new Region();
			region.setId(regionId);
			customers = customerService.findCustomersByRegion(region);
			
			if (customers == null) {
				log.error("Customer with Region id{} not found.", regionId);
				return ResponseEntity.notFound().build();
			}
		}
		//si se envia una categoria se filtra por categoriaId se listan
		return ResponseEntity.ok(customers);
	}
	
	// --------------------------    Retrieve Single Customer    ---------------------------//
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
		log.info("Fetching Customer with id {}", id);
		Customer customer = customerService.getCustomer(id);
		
		if (customer == null) {
			log.error("Customer with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}
	
	// --------------------------    Create Customer    ---------------------------//
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
		log.info("Creating Customer : {}", customer);
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		Customer customerDB = customerService.createCustomer(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
	}
	
	
	// --------------------------    Update Customer    ---------------------------//
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
		
		log.info("Updating Customer with id{}", id);
		Customer currentCustomer = customerService.getCustomer(id);
		
		if (currentCustomer == null) {
			log.error("Unable to update. Customer with id {} not found", id);
			return ResponseEntity.notFound().build();
		}
		
		customer.setId(id);
		currentCustomer = customerService.updateCustomer(customer);
		return ResponseEntity.ok(currentCustomer);
	}
	// --------------------------    Delete Customer    ---------------------------//
	
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
		log.info("Fetching & Deleted Customer with id {}", id);
		Customer customer = customerService.getCustomer(id);
		
		if (customer == null) {
			log.error("Unable to delete. Customer with id {} not found", id);
			return ResponseEntity.notFound().build();
		}
		
		customer = customerService.deleteCustomer(customer);
		return ResponseEntity.ok(customer);
	}
	// --------------------------    Create Errors    ---------------------------//
	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.code("01")
				.message(errors).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
