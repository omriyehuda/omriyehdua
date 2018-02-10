package com.example.demo.Facade;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponDoesntExistExeption;
@Component
public class CustomerFacade implements CouponClientFacade{

	
	@Autowired
	private CustomerDBDAO customerDbdao;
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		
		if (customerDbdao.login(name, password) == true && clientType == ClientType.Customer) { 
			 return this;
		}
		
		return null;
		
	}

	public void CustomerFacade(){
		
		
		
	}
	public void purchaseCoupon(Customer customer , Coupon c)throws CouponAllReadyExistException , CouponDoesntExistExeption{
		
		if(customer.getCoupons().contains(c)){
			throw new CouponAllReadyExistException("you can buy a coupon only once");
		}
		if (c.getAmount()<0){
			throw new CouponDoesntExistExeption("the coupon out of stok");
		}
				
		customerDbdao.buyCoupon(customer, c);
	}
	public Collection getAllPurchaseCoupons(Customer customer){
		return customer.getCoupons();
	}
	
	public List getAllPurchaseCouponsByType(Customer customer , CouponType type){
		return customerDbdao.getAllPurchaseCouponsByType(customer, type);
	}
	public Collection getAllPurchaseCouponsByPrice(Customer customer ,double price){
		return customerDbdao.getAllPurchaseCouponByPrice(customer, price);
	}
	
	
}
