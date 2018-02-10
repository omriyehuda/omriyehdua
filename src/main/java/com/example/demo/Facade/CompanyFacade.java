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
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CouponAllReadyExistException;
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
	
	public Collection getCouponsByType(Company company,CouponType type)throws CompanyDoesntExistExeption{
		if(companyDbdao.getCompany(company.getId())==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
		}
		
		return companyDbdao.getCouponsByType(company, type);
		
	}
	
	public Collection getCouponsByPrice(Company company,int price) throws PriceCantBeMinusException, CompanyDoesntExistExeption{
		if(companyDbdao.getCompany(company.getId())==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
		}
		if (price<0){
			throw new PriceCantBeMinusException("price need to be greater than 0");
		}
		return companyDbdao.getCouponsByPrice(company, price);
		
	}
	
	public Collection getCouponsByDate(Company company , Date date)throws CompanyDoesntExistExeption ,CompanyDoesntExistExeption {
		
		if(companyDbdao.getCompany(company.getId())==null){
			throw new CompanyDoesntExistExeption ( "company doesnt exist");
	
		}
		if (companyDbdao.getCouponsByDate(company, date).isEmpty()){
			throw new CompanyDoesntExistExeption("there is no coupons that match this date");
		}
		return companyDbdao.getCouponsByDate(company, date);
		
	}
	//login
	
	
	
}

