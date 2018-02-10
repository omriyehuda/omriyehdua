package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.Customer;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CustomerAllReadyExistException;
import com.example.demo.Exception.CustomerDoesntExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.UserNameNotMatchException;
@Component
public class AdminFacade implements CouponClientFacade{

	@Autowired
	private CompanyDBDAO companyDbdao;
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private CouponDBDAO couponDbdao;
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (name != ("omri")){
			throw new UserNameNotMatchException("the user name isnt correct");
		}
		if ( password!="0542515"){
			throw new PasswordNotCorrectException("password doesnt correct");	
		}
		
		if (name.equals("omri") && password.equals("0542515")&& clientType.equals(clientType.Admin)){
			return this;
		}
		return null;
	}
	
	public AdminFacade(){
		
	}

	public void createCompany(Company company) throws NameAllreadyExistException {//to do : remove coupons that buy by customers os the company

		
		if(companyDbdao.getCompanyByName(company.getCompanyName())!=null){
			throw new NameAllreadyExistException("there is a company with that name");
		}
		companyDbdao.createCompany(company);
	}
	
    public void removeCompany(Company company) throws CompanyDoesntExistExeption{

    	  	if(companyDbdao.getCompany(company.getId())!=null){
    		
    		Collection <Coupon> companyCouponsList = new ArrayList <Coupon>();
    		companyCouponsList =  company.getCoupons();
    		
    		for(Coupon c : companyCouponsList){
    			couponDbdao.removeCoupon(c);
    	}		
    		
    		companyDbdao.removeCompany(company);	


    	}
    	else throw new CompanyDoesntExistExeption("the company doesnt exist!");
    	
		}
    public void updateCompany(Company company)throws CompanyDoesntExistExeption{
    	
    	if(companyDbdao.getCompany(company.getId())!=null){
    		companyDbdao.updateCompany(company);
    		
    	}
    	else throw new CompanyDoesntExistExeption("company doenst exist");
    	}	
    
    
    public Company getCompany (int id){
    	
    	return companyDbdao.getCompany(id);
	}
    
    public Collection getAllCompanies(){
		Collection<Company> AllCompaniesList = new ArrayList<Company>();
		AllCompaniesList = companyDbdao.getAllCompanies();
    	
    	return AllCompaniesList;
		
	}

    public void createCustomer(Customer customer)throws NameAllreadyExistException,CustomerAllReadyExistException{
		if (customerDbdao.getCustomer(customer.getId())==null){
			
			if(customerDbdao.getCustomerByName(customer.getCustomerName())==null){
				
				customerDbdao.createCustomer(customer);	
			}
			else throw new NameAllreadyExistException("there is a customer by this name all ready, please chage");
		}
		else throw new CustomerAllReadyExistException("the customer you want to create allready exist");
	}
    public void updateCustomer(){
	
    }
    public void removeCustomer(Customer customer) throws CustomerDoesntExistExeption{
		
    	if (customerDbdao.getCustomer(customer.getId())!=null){
    		Collection <Coupon> customerCouponList = new ArrayList <Coupon> ();
    		customerCouponList = customer.getCoupons();
    		
    		for(Coupon c : customerCouponList){
    			couponDbdao.removeCoupon(c);
    		}
    	
    		customerDbdao.removeCustomer(customer);
    	}
    	else throw new CustomerDoesntExistExeption("the customer doesnt exist");

   	}
    
    public Customer getCustomer(int id){
		return customerDbdao.getCustomer(id);
   	}
    
    public Collection getAllCustomers(){
    	
    	Collection <Customer> listOfCustomers = new ArrayList<Customer>();
    	listOfCustomers = customerDbdao.getAllCustomers();
    	return listOfCustomers;
    	
    }
    
    //login
    
    
    
    
    
    
    
    
    
}