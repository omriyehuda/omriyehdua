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
import com.example.demo.Exception.CustomerDoesntExistExeption;
@Component
public class CustomerDBDAO implements CustomerDAO
{

	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CouponRepo couponRepo;
	
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
	public ArrayList getCoupons(Customer c) {
		
		return (ArrayList) c.getCoupons();
	}

	@Override
	public boolean login(String customer_name, String password) {
		
		if (customerRepo.findCustomerByNameAndPassword(customer_name, password)!=null ){
			return true;	
		}
		
		return false; 
	}
	
	public Customer getCustomerByName(String name){
		 return customerRepo.findCustomerByCustomerName(name);
	}
	
	
	public void buyCoupon(Customer customer , Coupon coupon){
		

		Date today = Calendar.getInstance().getTime();
		int couponId = coupon.getId();
		Coupon c = couponRepo.findCouponByIdAndTime(couponId,today );
		customer.getCoupons().add(c);
		
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
