package com.example.demo.WebService;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Facade.CustomerFacade;

@Component
@RestController
public class CustomerFacadeWS {

	@Autowired
	private CustomerFacade customerFacade;
	
	// ask if need CouponClientFacade login(String name, String password, ClientType clientType) method
	
	
	
	
	@RequestMapping( value = "/CustomerFacadeWS/purchaseCoupon", method = RequestMethod.POST)
	public void purchaseCoupon(Coupon c){
		
		customerFacade.purchaseCoupon(c);
	}
	
	@RequestMapping( value = "/CustomerFacadeWS/getAllPurchaseCoupons", method = RequestMethod.GET)
	public Collection getAllPurchaseCoupons(){
		
		return customerFacade.getAllPurchaseCoupons();
	}
	@RequestMapping( value = "/CustomerFacadeWS/getAllPurchaseCouponsByType", method = RequestMethod.GET)
	public List getAllPurchaseCouponsByType( CouponType type){
		
		return customerFacade.getAllPurchaseCouponsByType(type);
	}
	@RequestMapping( value = "/CustomerFacadeWS/getAllPurchaseCouponsByPrice", method = RequestMethod.GET)
	public Collection getAllPurchaseCouponsByPrice(double price){
		
		return customerFacade.getAllPurchaseCouponsByPrice(price);
		
	}
}
