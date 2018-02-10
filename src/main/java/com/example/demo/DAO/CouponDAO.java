package com.example.demo.DAO;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
@Component
public interface CouponDAO {
	
	
	void createCoupon (Coupon c);
	void removeCoupon (Coupon c);
	void updateCoupon (Coupon c);
	Coupon getCoupon (int id);
	Collection getAllCoupons();
	Collection getCouponByType(CouponType type);

	

}
