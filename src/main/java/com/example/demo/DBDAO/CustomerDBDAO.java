package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CustomerDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CustomerDoesntExistExeption;
@Component
public class CustomerDBDAO implements CustomerDAO
{

	Customer loggedInCustomer;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	TransactionsDBDAO transactionsDbdao;
	
	@Override
	public void createCustomer(Customer c) {
		customerRepo.save(c);
	
	}
	
	@Override
	public void removeCustomer(Customer c) {
		customerRepo.delete(c);
		
	}
	
	@Override
	public void updateCustomer(Customer c) throws CustomerDoesntExistExeption{
		if(customerRepo.exists(c.getId())==false){
			throw new CustomerDoesntExistExeption("the customer doesnt exist");
		}
		customerRepo.delete(c);
		customerRepo.save(c);
	}

	@Override
	public Customer getCustomer(int id) {
		
		
		return customerRepo.findCustomerById(id);
	}

	@Override
	public ArrayList getAllCustomers() {
		return (ArrayList) customerRepo.findAll();
	}

	@Override
	public ArrayList getCoupons() {
		
		return (ArrayList) loggedInCustomer.getCoupons();
	}
	
	public Coupon getCouponById(int id){
		
		return couponRepo.findCouponById(id);
	}

	@Override
	public boolean login(String customer_name, String password) {

		loggedInCustomer = customerRepo.findCustomerByNameAndPassword(customer_name, password);  
		if (loggedInCustomer!=null ){
			
			return true;	
		}
		
		return false; 
	}
	
	public Customer getCustomerByName(String name){
		 return customerRepo.findCustomerByCustomerName(name);
	}
	
	
	public void buyCoupon(Coupon coupon){
	
		if(customerRepo.CustomerContainCoupon(loggedInCustomer.getId(), coupon.getId())!=null){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponAllReadyExistException("you can buy a coupon only once");
		}
		
		coupon.setAmount(coupon.getAmount()-1);
		this.loggedInCustomer.getCoupons().add(coupon);
		customerRepo.save(loggedInCustomer);
		couponRepo.save(coupon);
		transactionsDbdao.writeToTableCustomer("purchaseCoupon", true, EnumFacade.CustomerFacade, loggedInCustomer);
		
		
	}
	
	public Coupon getCouponByIdAndTimeAvailable(Coupon coupon){
		Date today = Calendar.getInstance().getTime();
		return couponRepo.findCouponByIdAndTime(coupon.getId(), today);
	}

	public List getAllPurchaseCouponsByType(Customer customer , CouponType type){
		int cust_id = customer.getId();
		
		return customerRepo.findAllCouponFromCusttomerByType(cust_id, type);
	}
	
	public List getAllPurchaseCouponByPrice (Customer customer , double price){
		
		int id  = customer.getId();
		return customerRepo.findAllPurchaseCouponsByPrice(id, price);
	}
}
