package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponDoesntAvailableExeption;
import com.example.demo.Exception.CouponDoesntExistExeption;
import com.example.demo.Exception.CouponTitleAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.PriceCantBeMinusException;
import com.example.demo.Exception.UserNameNotMatchException;
/**
 * CompanyFacade implements CouponClientFacade
 * @author omri
 *
 */
@Component
public class CompanyFacade implements CouponClientFacade{

	@Autowired
	private CompanyDBDAO companyDbdao;
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private CouponDBDAO couponDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;

/**
 * login method , login to the coupon system by company client
 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
			
		if (companyDbdao.login(name, password) == true && clientType == ClientType.Company  ){
			return this;
		}
		return null;
	}

	public CompanyFacade(){
		
	}
	
	
/**
 * create coupon	
 * @param c
 * @throws CouponAllReadyExistException
 * @throws CouponTitleAllreadyExistException
 */
	
	public void createCoupon(Coupon c) throws CouponAllReadyExistException ,CouponTitleAllreadyExistException{
		if (couponDbdao.getCoupon(c.getId())!=null){
			throw new CouponAllReadyExistException("the coupon all ready exist");	
		}
		if (couponDbdao.getCouponByTitle(c.getTitle())!=null){
			throw new CouponTitleAllreadyExistException("coupon title all ready exist , please change");
		}
		else {
			couponDbdao.createCoupon(c);
			companyDbdao.getLastCompany().getCoupons().add(c);
			companyDbdao.updateCompany(companyDbdao.getLastCompany());
			
		}
	}
/**
 * remove coupon from BD 	
 * @param c
 * @throws CouponDoesntExistExeption
 */
	public void removeCoupon(Coupon c) throws CouponDoesntExistExeption{
		System.out.println(c.getId());
		if(couponDbdao.getCoupon(c.getId())!=null){
			couponDbdao.removeCoupon(c);
		}
		else throw new CouponDoesntExistExeption("the coupon doesnt exist");
		
	}
/**
 * update coupon object	
 * @param c
 * @throws CouponDoesntExistExeption
 */
	public void updateCoupon(Coupon c)throws CouponDoesntExistExeption{
		if(couponDbdao.getCoupon(c.getId())==null){
			
			throw new CouponDoesntExistExeption("coupon doesnt exist");
		}
		
		couponDbdao.updateCoupon(c);
	}
	
/**
 * get company from DB by company id
 * @param id
 * @return
 * @throws CompanyDoesntExistExeption
 */
		
	public Company getCompany(int id)throws CompanyDoesntExistExeption{
		
		if(companyDbdao.getCompany(id)==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
		}
		
		return  companyDbdao.getCompany(id);
	}
	
/**
 * 	get all companies from DB 
 * @return List of companies
 * @throws CouponDoesntExistExeption
 */

	public Collection getAllCoupons() throws CouponDoesntExistExeption{
		
		if(companyDbdao.getCoupons().isEmpty()) {
			throw new CouponDoesntExistExeption("thre is no coupons ");
		}
		 return companyDbdao.getCoupons();
	}
/**
 * get coupons from DB by type 
 * @param type
 * @return Collection coupon object
 * @throws CouponDoesntExistExeption
 */
	public Collection getCouponsByType(CouponType type)throws CouponDoesntExistExeption{
		
		
		if(companyDbdao.getCouponsByType(type).isEmpty()){
			throw new CouponDoesntExistExeption ( "Coupons doesnt exist");
		}
		
		return companyDbdao.getCouponsByType( type);
		
	}
	
/**
 * get coupons from DB by price 
 * @param price
 * @return Collection coupons objects
 * @throws PriceCantBeMinusException
 * @throws CouponDoesntExistExeption
 */
	
	public Collection getCouponsByPrice(int price) throws PriceCantBeMinusException, CouponDoesntExistExeption{
		if (price < 0 ){
			throw new PriceCantBeMinusException("price need to be greater than 0");
		}
		if(companyDbdao.getCouponsByPrice(price).isEmpty()){
			throw new CouponDoesntExistExeption ( "Coupons doesnt exist check the price");
		}
		return companyDbdao.getCouponsByPrice( price);
		
	}
	
/**
 * get coupons from DB by date .
 * @return Collection coupons objects
 * @throws CouponDoesntAvailableExeption
 */
	public Collection getCouponsByDate()throws CouponDoesntAvailableExeption{
		
		if(companyDbdao.getCouponsByDate().isEmpty()){
			throw new CouponDoesntAvailableExeption ( "there is no coupons that match this date");
	
		}
		
		return companyDbdao.getCouponsByDate();
		
	}
	
	public Coupon getCoupon (int id){
		return companyDbdao.getCoupon(id);
	
	}
	
}

