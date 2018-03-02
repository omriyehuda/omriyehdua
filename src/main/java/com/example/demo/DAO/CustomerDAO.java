package com.example.demo.DAO;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Customer;
@Component
public interface CustomerDAO {
//	@Autowired
//	Company company;
//	@Autowired
//	Customer customer;
//	List listOfCompanies= new List<customer>();
	
	
	void createCustomer (Customer c);
	void removeCustomer (Customer c);
	void updateCustomer (Customer c);
	Customer getCustomer (int id);
	Collection getAllCustomers();
	Collection getCoupons();
	boolean login (String customer_name ,String password);
	

}
