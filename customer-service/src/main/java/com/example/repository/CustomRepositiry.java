package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Customer;
import com.example.entity.Region;

public interface CustomRepositiry extends JpaRepository<Customer, Long> {

	public Customer findByNumberID(String numberID);
	public List<Customer> findByLastName(String lastName);
	public List<Customer> findByRegion(Region region);
}
