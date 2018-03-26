package com.example.demo.Entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * @Author:Omri Yehuda 
 */
public interface CompanyRepo extends CrudRepository <Company,Integer>{
	
/**
 * find on DB Company object by its id
 * @param id
 * @return Company object
 */
	Company findCompanyById(int id);
	
/**
 *find on DB Company object by its name
 * @param name
 * @return Company object
 */
	Company findCompanyByCompanyName(String name);
/**
 * Get all the coupons of the company that there price less than entered price.
 * @param comp_id
 * @param price
 * @return List of coupons objects.
 */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.price <= :price) ")
	List<Coupon> getAllCouponsByPrice(@Param("id")int comp_id,@Param("price")double price);
/**
 * Get all the coupons of the company that match the entered CouponType
 * @param comp_id
 * @param type
 * @return List of Coupons object
 */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.type =:type")
	List<Coupon> getCouponsByType(@Param("id")int comp_id,@Param("type")CouponType type);
/**
 * Get all coupons of the company that entered endDate before the coupons endDate 
 * @param comp_id
 * @param date
 * @return List of Coupons object.
 */
	@Query ("SELECT COUPONS FROM COMPANIES COMP INNER JOIN COMP.coupons AS COUPONS WHERE COMP.id = :id AND COUPONS.endDate >=:date  ")
	List<Coupon> getCouponsByDate(@Param("id")int comp_id,@Param("date")LocalDate date);
/**
 * Get Company object from DB by name and password , the method used by login method
 * @param company_name
 * @param company_password
 * @return Company object
 */
	@Query ("SELECT c FROM COMPANIES c WHERE c.companyName = :company_name AND c.password = :company_password")
	Company findCompanyByNameAndPassword(@Param ("company_name") String company_name , @Param("company_password")String company_password);

	
	
}
