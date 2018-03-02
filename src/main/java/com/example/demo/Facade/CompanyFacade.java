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
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
			
		if (companyDbdao.login(name, password) == true && clientType == ClientType.Company  ){
			return this;
		}
		return null;
	}

	public CompanyFacade(){
		
	}
	
	
	
	
	public void createCoupon(Coupon c) throws CouponAllReadyExistException ,CouponTitleAllreadyExistException{
		if (couponDbdao.getCoupon(c.getId())!=null){
			throw new CouponAllReadyExistException("the coupon all ready exist");	
		}
		if (couponDbdao.getCouponByTitle(c.getTitle())!=null){
			throw new CouponTitleAllreadyExistException("coupon title all ready exist , please change");
		}
		else {
			couponDbdao.createCoupon(c);
		}
	}
	
	
	
	
	
	
	public void removeCoupon(Coupon c) throws CouponDoesntExistExeption{
		
		if(couponDbdao.getCoupon(c.getId())!=null){
			couponDbdao.removeCoupon(c);
		}
		else throw new CouponDoesntExistExeption("the coupon doesnt exist");
		
	}
	
	
	
	
	
	
	public void updateCoupon(Coupon c)throws CouponDoesntExistExeption{
		if(couponDbdao.getCoupon(c.getId())==null){
			
			throw new CouponDoesntExistExeption("coupon doesnt exist");
		}
		
		couponDbdao.updateCoupon(c);
	}
	
	
	
	
	
	public Company getCompany(int id)throws CompanyDoesntExistExeption{
		
		if(companyDbdao.getCompany(id)==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
		}
		
		return  companyDbdao.getCompany(id);
	}
	
	
	
	
	public Collection getAllCoupons(Company company)throws CompanyDoesntExistExeption{
		
		if(companyDbdao.getCompany(company.getId())==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
		}
		
		Collection<Coupon> tempListOfCoupons = new ArrayList<Coupon>();
		tempListOfCoupons = company.getCoupons();
		
		return tempListOfCoupons;
	}
	
	
	
	
	public Collection getCouponsByType(CouponType type)throws CouponDoesntExistExeption{
		
		
		if(companyDbdao.getCouponsByType(type)==null){
			throw new CompanyDoesntExistExeption ( "Coupons doesnt exist");
		}
		
		return companyDbdao.getCouponsByType( type);
		
	}
	
	
	
	
	public Collection getCouponsByPrice(int price) throws PriceCantBeMinusException, CouponDoesntExistExeption{
		if(companyDbdao.getCouponsByPrice(price)==null){
			throw new CompanyDoesntExistExeption ( "Coupons doesnt exist check the price");
		}
		if (price<0){
			throw new PriceCantBeMinusException("price need to be greater than 0");
		}
		return companyDbdao.getCouponsByPrice( price);
		
	}
	
	
	
	
	
	
	public Collection getCouponsByDate()throws CouponDoesntAvailableExeption{
		
		if(companyDbdao.getCouponsByDate()==null){
			throw new CompanyDoesntExistExeption ( "there is no coupons that match this date");
	
		}
		
		return companyDbdao.getCouponsByDate();
		
	}

	
	
	
}

