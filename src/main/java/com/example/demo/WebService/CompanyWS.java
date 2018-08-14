package com.example.demo.WebService;

import java.util.Collection;

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
import com.example.demo.Entities.CouponType;
import com.example.demo.Facade.ClientType;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.system.CouponSystem;

@Component
@RestController
@CrossOrigin("*")
public class CompanyWS {

	
	@Autowired
	private CouponSystem couponSystem;
	
	
	// ask if need CouponClientFacade login(String name, String password, ClientType clientType) method
	private CompanyFacade getFacade(){
//		 
		 CompanyFacade af = (CompanyFacade) couponSystem.login("rrd", "1234", ClientType.Company);
		 return af;
	}
	
	
	@RequestMapping( value = "/CompanyWS/company/createCoupon", method = RequestMethod.POST)//vv
	public void createCoupon(@RequestBody Coupon c){
		CompanyFacade af= getFacade();
	
		af.createCoupon(c);
	}
	
	@RequestMapping( value = "/CompanyWS/company/removeCoupon/{id}", method = RequestMethod.DELETE)//v
	public void removeCoupon(@PathVariable ("id") int id){
		CompanyFacade af= getFacade();
		Coupon coupon = af.getCoupon(id);
		af.removeCoupon(coupon);
	}
	
	@RequestMapping( value = "/CompanyWS/company/updateCoupon", method = RequestMethod.PUT)//vv
	public void updateCoupon(@RequestBody Coupon c){
		CompanyFacade af= getFacade();
		af.updateCoupon(c);
	}
	
	@RequestMapping( value = "/CompanyWS/company/getCompany/{id}", method = RequestMethod.GET)//vv
	public Company getCompany(@PathVariable ("id") int id){
		CompanyFacade af= getFacade();
		
		return af.getCompany(id);
	}
	
	@RequestMapping( value = "/CompanyWS/company/getAllCoupons", method = RequestMethod.GET)//vv
	public Collection getAllCoupons(){
		
		CompanyFacade af= getFacade();

		return af.getAllCoupons();
	}
	
	@RequestMapping( value = "/CompanyWS/company/getCouponsByType/{type}", method = RequestMethod.GET)//vv
	public Collection getCouponsByType(@PathVariable ("type") String type){
		CompanyFacade af= getFacade();
		return af.getCouponsByType(CouponType.valueOf(type));
	}
	
	@RequestMapping( value = "/CompanyWS/company/getCouponsByPrice/{price}", method = RequestMethod.GET)//vv
	public Collection getCouponsByPrice(@PathVariable ("price") int price){
		CompanyFacade af= getFacade();
		return af.getCouponsByPrice(price);
	}
	
	@RequestMapping( value = "/CompanyWS/company/getCouponsByDate/{date}", method = RequestMethod.GET)
	public Collection getCouponsByDate(@PathVariable ("date") String date1){//simple date format
		CompanyFacade af= getFacade();
		return af.getCouponsByDate();
	}
}
