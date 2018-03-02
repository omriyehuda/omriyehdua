package com.example.demo.Facade;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponDoesntAvailableExeption;
import com.example.demo.Exception.CouponDoesntExistExeption;
@Component
public class CustomerFacade implements CouponClientFacade{

	
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {

		if (customerDbdao.login(name, password) == true && clientType == ClientType.Customer) { 
			transactionsDbdao.writeToTable("login", true, EnumFacade.CustomerFacade);
		
			return this;	 
		}
		
		return null;
	}

	
	public void CustomerFacade(){
		
	}
	
	
	
	public void purchaseCoupon(Coupon c){
		
		if (c.getAmount()<0){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponDoesntExistExeption("the coupon out of stok");
		}
		if (customerDbdao.getCouponByIdAndTimeAvailable(c)==null){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponDoesntAvailableExeption("sorry the coupon end date ended"); 
		}

		customerDbdao.buyCoupon( c);
	}
	
	
	
	public Collection getAllPurchaseCoupons(Customer customer){
		transactionsDbdao.writeToTableCustomer("getAllPurchaseCoupons", true, EnumFacade.CustomerFacade, customer);
		return customer.getCoupons();
	}
	
	
	
	public List getAllPurchaseCouponsByType(Customer customer , CouponType type){
		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponsByType", true, EnumFacade.CustomerFacade, customer);
		return customerDbdao.getAllPurchaseCouponsByType(customer, type);
	}
	
	
	
	public Collection getAllPurchaseCouponsByPrice(Customer customer ,double price){
		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponsByPrice", true, EnumFacade.CustomerFacade, customer);
		return customerDbdao.getAllPurchaseCouponByPrice(customer, price);
	}
	
	
}
