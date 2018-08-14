package com.example.demo.WebService;

import java.util.Collection;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.Customer;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.ClientType;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CouponClientFacade;
import com.example.demo.system.CouponSystem;




@RestController
@CrossOrigin("*")
public class AdminWS {

	
	@Autowired
	private CouponSystem couponSystem;
	
	
	
	
	
	private AdminFacade getFacade(){
		
		 AdminFacade af = (AdminFacade) couponSystem.login("omri", "0542515", ClientType.Admin);
		 return af;
	}
	
	
	
	@RequestMapping( value = "/AdminWS/admin/creatCompany", method = RequestMethod.POST)//v
	public void createCompany(@RequestBody Company company) {
		
		AdminFacade af= getFacade();
		af.createCompany(company);
		
	}
	
	
	@RequestMapping( value = "/AdminWS/admin/removeCompany/{id}", method = RequestMethod.DELETE)//v
	public void removeCompany(@PathVariable("id") int id){
		AdminFacade af= getFacade();
		Company company = af.getCompany(id);
		af.removeCompany(company);
	}
	
	@RequestMapping( value = "/AdminWS/admin/updateCompany", method = RequestMethod.PUT)//vv
	public void updateCompany(@RequestBody Company company ) {
		AdminFacade af= getFacade();

		af.updateCompany(company);
		
	}

	
	@RequestMapping( value = "/AdminWS/admin/getCompany/{id}", method = RequestMethod.GET)//v
	public Company getCompany(@PathVariable("id") int id){
		AdminFacade af= getFacade();
		return af.getCompany(id);
		
		
	}
	
	@RequestMapping( value = "/AdminWS/admin/getAllCompanies", method = RequestMethod.GET)//v
	public Collection getAllCompanies(){
		AdminFacade af= getFacade();
		return af.getAllCompanies();
		
	}
	
	@RequestMapping( value = "/AdminWS/admin/createCustomer", method = RequestMethod.POST)//v
	public void createCustomer(@RequestBody Customer customer){
		AdminFacade af= getFacade();
		af.createCustomer(customer);
		
	}
	
	@RequestMapping( value = "/AdminWS/admin/updateCustomer", method = RequestMethod.PUT)//xxx
	public void updateCustomer(Customer customer){
		AdminFacade af= getFacade();
		af.updateCustomer(customer);
	}
	
	
	@RequestMapping( value = "/AdminWS/admin/removeCustomer/{id}", method = RequestMethod.DELETE)//vv
	public void removeCustomer(@PathVariable("id") int id){
		AdminFacade af= getFacade();
		Customer customer = af.getCustomer(id);
		af.removeCustomer(customer);
		
	}
	
	
	@RequestMapping( value = "/AdminWS/admin/getCustomer/{id}", method = RequestMethod.GET)//vv
	public Customer getCustomer(@PathVariable("id") int id){
		AdminFacade af= getFacade();
		return af.getCustomer(id);
	}
	
	@RequestMapping( value = "/AdminWS/admin/getAllCustomers", method = RequestMethod.GET)//vv
	public Collection getAllCustomers(){
		AdminFacade af= getFacade();
		return af.getAllCustomers();
	}
	
}
