package com.example.demo.DBDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CustomerDAO;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CustomerDoesntExistExeption;

/**
 * CustomerDBDAO implements CustomerDAO
 * 
 * @author omri all the method to control customer user from CustomerFacade.
 */
@Component
public class CustomerDBDAO implements CustomerDAO {

	private Customer loggedInCustomer;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private CouponRepo couponRepo;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;

	/**
	 * method to create a customer
	 * 
	 * @param c- created customer
	 */
	@Override
	public void createCustomer(Customer c) {
		customerRepo.save(c);

	}

	/**
	 * method to remove customer
	 * 
	 * @param c- removed customer
	 */

	@Override
	public void removeCustomer(Customer c) {
		customerRepo.delete(c);

	}

	/**
	 * method to update customer password and list of coupons
	 * 
	 * @param c - updated customer
	 */
	@Override
	public void updateCustomer(Customer c) throws CustomerDoesntExistExeption {

		Customer tempCustomer = customerRepo.findCustomerById(c.getId());
		tempCustomer.setPassword(c.getPassword());
		tempCustomer.setCoupons(c.getCoupons());

		customerRepo.save(tempCustomer);
	}

	/**
	 * method that get an id and return customer from DB.
	 * 
	 * @param id- customer id
	 * @return - customer object
	 */
	@Override
	public Customer getCustomer(int id) {

		return customerRepo.findCustomerById(id);
	}

	/**
	 * method to get all customers from DB.
	 * 
	 * @return Collection <Customer> of customer objects
	 */
	@Override
	public ArrayList getAllCustomers() {
		return (ArrayList) customerRepo.findAll();
	}

	/**
	 * method to get all the coupons of the customer that logged in to the
	 * coupons system
	 * 
	 * @return - Collection <Coupon> of coupons objects
	 */
	@Override
	public Collection getCoupons() {
		transactionsDbdao.writeToTableCustomer("getCoupons", true, EnumFacade.CustomerFacade,
				loggedInCustomer.toString());
		return loggedInCustomer.getCoupons();
	}

	public Coupon getCouponById(int id) {

		return couponRepo.findCouponById(id);
	}

	/**
	 * method to logged in to coupons system
	 * 
	 * @param customer_name - user name
	 * @param password - user password
	 * @return true/false
	 */
	@Override
	public boolean login(String customer_name, String password) {

		loggedInCustomer = customerRepo.findCustomerByNameAndPassword(customer_name, password);
		if (loggedInCustomer != null) {

			return true;
		}

		return false;
	}
	/**
	 * get customer object from DB by title/name
	 * @return - customer object
	 */
	public Customer getCustomerByName(String name) {
		return customerRepo.findCustomerByCustomerName(name);
	}
	/**
	 * method that admit coupon object to customer entity
	 * @return - Coupon object
	 */
	public void buyCoupon(Coupon coupon) {

		if (customerRepo.CustomerContainCoupon(loggedInCustomer.getId(), coupon.getId()) != null) {
			transactionsDbdao.writeToTable("purchaseCoupon", false, EnumFacade.CustomerFacade);
			throw new CouponAllReadyExistException("you can buy a coupon only once");
		}

		coupon.setAmount(coupon.getAmount() - 1);
		couponRepo.save(coupon);
		loggedInCustomer.getCoupons().add(coupon);
		this.updateCustomer(loggedInCustomer);
		transactionsDbdao.writeToTableCustomer("purchaseCoupon", true, EnumFacade.CustomerFacade,
				loggedInCustomer.toString());

	}
	/**
	 * helpful method to get the last logged in customer object
	 * @return - customer object
	 */
	public Customer getLoggedInLastCustomer() {

		return loggedInCustomer;
	}
	/**
	 * method - to get an available coupon 
	 * @return - Coupon object
	 */
	public Coupon getCouponByIdAndTimeAvailable(Coupon coupon) {
		Date today = Calendar.getInstance().getTime();
		return couponRepo.findCouponByIdAndTime(coupon.getId(), new Date());
	}
	/**
	 * get all purchase coupon
	 * @return - List <Coupon> coupons
	 */
	public List getAllPurchaseCouponsByType(CouponType type) {

		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponsByType", true, EnumFacade.CustomerFacade,
				loggedInCustomer.toString());
		return customerRepo.findAllCouponFromCusttomerByType(loggedInCustomer.getId(), type);
	}
	/**
	 * get all purchase coupon by price 
	 * @return - List <Coupon> couponsByPrice
	 */
	public List getAllPurchaseCouponByPrice(double price) {

		transactionsDbdao.writeToTableCustomer("getAllPurchaseCouponByPrice", true, EnumFacade.CustomerFacade,
				loggedInCustomer.toString());
		return customerRepo.findAllPurchaseCouponsByPrice(loggedInCustomer.getId(), price);
	}
}
