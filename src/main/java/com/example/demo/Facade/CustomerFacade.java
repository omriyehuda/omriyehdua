package com.example.demo.Facade;

import java.util.ArrayList;
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
/**
 * CustomerFacade implements CouponClientFacade
 * @author omri
 *
 */
@Component
public class CustomerFacade implements CouponClientFacade{

	
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
/**
 * login method , login to the coupon system by customer client	
 */
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
	
	
/**
 * method that admit coupon object to customer entity	
 * @param c
 * @throws CouponDoesntExistExeption
 * @throws CouponDoesntAvailableExeption
 */
	public void purchaseCoupon(Coupon c)throws CouponDoesntExistExeption , CouponDoesntAvailableExeption{
		
		if (customerDbdao.getCouponById(c.getId())==null || c.getAmount()<=0){ 
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponDoesntExistExeption("the coupon out of stok or doesnt exist");
		}
		if (customerDbdao.getCouponByIdAndTimeAvailable(c)==null){
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponDoesntAvailableExeption("sorry the coupon end date ended"); 
		}
		
		customerDbdao.buyCoupon( c);
	}
	
	
/**
 * get all purchase coupons	
 * @return Collections of coupons
 */
	public Collection getAllPurchaseCoupons(){
		
		return customerDbdao.getCoupons();
	}
	
/**
 * 	get all purchase coupons by type
 * @param type
 * @return Collections of coupons
 */
	
	public List getAllPurchaseCouponsByType( CouponType type){
		
		return customerDbdao.getAllPurchaseCouponsByType(type);
	}
	
/**
 * get all purchase coupons by price
 * @param price
 * @return  Collections of coupons
 */
	
	public Collection getAllPurchaseCouponsByPrice(double price){
		
		return customerDbdao.getAllPurchaseCouponByPrice( price);
	}
	
	
}
