package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CouponDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CouponDoesntExistExeption;
@Component
public class CouponDBDAO implements  CouponDAO{
	@Autowired
	CouponRepo couponRepo;
	
	@Override
	public void createCoupon(Coupon c) {
		couponRepo.save(c);
		
	}

	@Override
	public void removeCoupon(Coupon c) {
		couponRepo.delete(c);
		
	}

	@Override
	public void updateCoupon(Coupon c) {		
		Coupon tempCoupon = new Coupon();
		tempCoupon = couponRepo.findCouponById(c.getId());
		tempCoupon.setEndDate(c.getEndDate());
		tempCoupon.setPrice(c.getPrice());
		couponRepo.save(tempCoupon);
	}

	@Override
	public Coupon getCoupon(int id) {
		Coupon c = couponRepo.findOne(id);
		return c;
	}

	@Override
	public ArrayList getAllCoupons() {//check if i need to put a loop
		
		return (ArrayList) couponRepo.findAll();
	}

	@Override
	public ArrayList getCouponByType (CouponType type) {
		return null;
	}

	public Coupon getCouponByTitle(String Title){
		return couponRepo.findCouponByTitle(Title);
	}
}
