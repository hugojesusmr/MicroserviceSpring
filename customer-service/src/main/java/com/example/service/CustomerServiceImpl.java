package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.entity.Region;
import com.example.repository.CustomRepositiry;



@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomRepositiry customRepository;
	@Override
	public List<Customer> findCustomerAll() {
		return customRepository.findAll();
	}

	@Override
	public List<Customer> findCustomersByRegion(Region region) {
		return customRepository.findByRegion(region);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		Customer customerDB = customRepository.findByNumberID(customer.getNumberID());
		if (customerDB != null) {
			return customerDB;
		}
		
		customer.setState("CREATED");
		customerDB = customRepository.save(customer);
		return customerDB;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer customerDB = getCustomer(customer.getId());
		if (customerDB == null) {
			return null;
		}
		customerDB.setFirstName(customer.getFirstName());
		customerDB.setLastName(customer.getLastName());
		customerDB.setEmail(customer.getEmail());
		
		return customRepository.save(customerDB);
	}

	@Override
	public Customer deleteCustomer(Customer customer) {
		Customer customerDB = getCustomer(customer.getId());
		if (customerDB == null) {
			return null;
		}
		customer.setState("DETETED");
		return customRepository.save(customer);
	}

	@Override
	public Customer getCustomer(Long id) {
		
		return customRepository.findById(id).orElse(null);
	}

}
