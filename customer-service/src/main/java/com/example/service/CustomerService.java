package com.example.service;

import java.util.List;

import com.example.entity.Customer;
import com.example.entity.Region;

public interface CustomerService {
	public List<Customer> findCustomerAll();
	public List<Customer> findCustomersByRegion(Region region);
	public Customer createCustomer(Customer customer);
	public Customer updateCustomer(Customer customer);
	public Customer deleteCustomer(Customer customer);
	public Customer getCustomer(Long id);
}
