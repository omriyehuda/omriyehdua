package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * @Author: 
 */
public interface CompanyRepo extends CrudRepository <Company,Integer>{
	
	
	Company findCompanyById(int id);
	
	Company findCompanyByCompanyName(String name);
	
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.price <= :price) ")
	List<Coupon> getAllCouponsByPrice(@Param("id")int comp_id,@Param("price")double price);
	
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.type =:type")
	List<Coupon> getCouponsByType(@Param("id")int comp_id,@Param("type")CouponType type);

	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.endDate >=:date  ")
	List<Coupon> getCouponsByDate(@Param("id")int comp_id,@Param("date")LocalDate date);
	
	@Query ("SELECT c FROM COMPANIES c WHERE c.companyName = :company_name AND c.password = :company_password")
	Company findCompanyByNameAndPassword(@Param ("company_name") String company_name , @Param("company_password")String company_password);

	
	
}
