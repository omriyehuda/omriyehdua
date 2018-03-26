package com.example.demo.Entities;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * CustomerRepo extends CrudRepository
 * @author omri
 *
 */
public interface CustomerRepo extends CrudRepository<Customer , Integer>  {
/**
 * get customer object from DB by id	
 * @param id
 * @return Customer object
 */
	
	Customer findCustomerById(int id);
/**
 * get customer object from BD by cstomer name
 * @param name
 * @return Customer object
 */
	Customer findCustomerByCustomerName(String name);
/**
 * get all coupons from BD by type	
 * @param cust_id
 * @param couponType
 * @return List of coupons
 */
	
	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.type = :type") 
	List <Coupon> findAllCouponFromCusttomerByType(@Param("customerId")int cust_id,@Param("type") CouponType couponType);
/**
 * get all coupons that exist in customer collection field and there price less than the entered price
 * @param cust_id
 * @param price
 * @return List of coupons
 */
	
	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.price <= :price") 
	List <Coupon> findAllPurchaseCouponsByPrice(@Param("customerId")int cust_id,@Param("price") double price);
/**
 *  Get Customer object from DB by name and password , the method used by login method
 * @param customer_name
 * @param cust_password
 * @return  Customer object
 */
	@Query ("SELECT c FROM CUSTOMERS c WHERE c.customerName = :customer_name AND c.password = :customer_pass")
	Customer findCustomerByNameAndPassword(@Param("customer_name") String customer_name , @Param("customer_pass")String cust_password);
/**
 * get coupon that exist in customer collection field by coupon id and customer id 
 * @param customerId
 * @param coupId
 * @return Coupon object
 */
	@Query ("SELECT coup FROM CUSTOMERS cust INNER JOIN cust.coupons AS coup WHERE cust.id = :customerId AND coup.id = :coupId")
	Coupon CustomerContainCoupon (@Param("customerId")int customerId,@Param("coupId") int coupId);
	
}
