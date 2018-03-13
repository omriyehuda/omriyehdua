package com.example.demo.Entities;


import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CouponRepo extends CrudRepository<Coupon, Integer>{

	Coupon findCouponByTitle(String title);
	
	Coupon findCouponById(int id);
	
	@Query ("SELECT c FROM COUPONS c WHERE c.id = :couponId AND c.endDate >= :buyDate")
	Coupon findCouponByIdAndTime (@Param("couponId") int couponId , @Param ("buyDate") LocalDate buyDate);
	
//	@Query ("SELECT c FROM COUPONS c WHEREc.endDate >= :buyDate")
//	Coupon findCouponByTimeAvailable (@Param ("buyDate") Date buyDate);
	
	
	
	
}


