package com.example.demo.Entities;


import java.time.LocalDate;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * CouponRepo extends CrudRepository
 * @author omri
 *
 */
public interface CouponRepo extends CrudRepository<Coupon, Integer>{
/**
 * find coupon object from DB by title 
 * @param title
 * @return Coupon object
 */
	Coupon findCouponByTitle(String title);
/**
 * find Coupon from DB by id	
 * @param id
 * @return Coupon object
 */
	Coupon findCouponById(int id);
/**
 * find Coupon from DB by id and endDate 
 * @param couponId
 * @param buyDate
 * @return Coupon object
 */
	@Query ("SELECT c FROM COUPONS c WHERE c.id = :couponId AND c.endDate >= :buyDate")
	Coupon findCouponByIdAndTime (@Param("couponId") int couponId , @Param ("buyDate") LocalDate buyDate);
/**
 * delete coupon from DB by endDate , method of daily expired	
 * @param endDate
 */
	@Transactional
	@Modifying
	@Query("DELETE FROM COUPONS c WHERE c.endDate <= :coupon_endDate")
	void deleteCouponByEndDate (@Param("coupon_endDate") Date endDate);
}
	
	
	
	



