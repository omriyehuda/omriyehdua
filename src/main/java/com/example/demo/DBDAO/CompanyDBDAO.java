package com.example.demo.DBDAO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.CompanyDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponType;
import com.example.demo.Exception.CompanyDoesntExistExeption;
/**
 * CompanyDBDAO implements CompanyDAO 
 * @author omri
 * all the method to control company user from CompanyFacade. 
 */
@Component
public class CompanyDBDAO implements CompanyDAO{
	
	
	private Company loggedInCompany;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
	/**
	 * a list to save all ccompanies. 
	 */
	ArrayList<Company> listOfCompanies =new ArrayList<Company>();
	
	/**
	 * an empty constructor
	 */
	public CompanyDBDAO(){
		
	}
	/**
	 * method that create a company and saved in DB.
	 * @param c - is the company that method create.
	 */
	@Override
	public void createCompany(Company c) {
		companyRepo.save(c);
		
	}
	/**
	 * method to get company object by the company name
	 * @param name - String company name
	 * @return - company object
	 */
	public Company getCompanyByName (String name){
	
		return companyRepo.findCompanyByCompanyName(name);

	}
	
	/**
	 * method that remove a company and saved in DB.
	 * @param c - is the company that the method remove.
	 */

	@Override
	public void removeCompany(Company c) {
	
		companyRepo.delete(c);
		
	}

	/**
	 * method to update a company. the method update the coupons list, the password and the email.
	 * @param c - updated company
	 */
	@Override
	public void updateCompany(Company c){
		
		Company tempCompany = companyRepo.findCompanyByCompanyName(c.getCompanyName());
		tempCompany.setCoupons(c.getCoupons());
		tempCompany.setEmail(c.getEmail());
		tempCompany.setPassword(c.getPassword());
		
		companyRepo.save(tempCompany);
	}

	/**
	 * method that get an id and return a company.
	 * @param id - company id
	 * @return - company object
 	 */
	@Override
	public Company getCompany(int id) {
		return companyRepo.findCompanyById(id);
	}

	/**
	 * method to get all the companies in coupon system.
	 * @return - list of companies object.
	 */
	@Override
	public Collection<Company> getAllCompanies() {
		
		return (ArrayList<Company>) companyRepo.findAll();
	}

	/**
	 * method to get all coupons from the last clientType.company that logged in
	 * @return - list of coupons of clientType.company
	 */
	@Override
	public Collection getCoupons() {
		
		return loggedInCompany.getCoupons();
	}

	/**
	 * logged in to coupons system.
	 * @param company_name - name of the company user
	 * @param password - password of the user
	 * @return
	 */
	@Override
	public boolean login(String company_name, String password) {
		
		if (companyRepo.findCompanyByNameAndPassword(company_name, password)!=null ){
			loggedInCompany = companyRepo.findCompanyByNameAndPassword(company_name,password);
			
			return true ;
		}
		
		return false;
	}

	/**
	 * method to get coupon by specific type from the logged in company user
	 * @param type
	 * @return - List of coupons objects
	 */
	public List getCouponsByType(CouponType type){
		
		return companyRepo.getCouponsByType(loggedInCompany.getId(), type);
		
	}
	
	/**
	 * method to get specific coupons up to a certain price from the logged in company user 
	 * @param price - price
	 * @return - List of coupons objects
	 */

		
	public List getCouponsByPrice ( double price )	{
		
		return companyRepo.getAllCouponsByPrice(loggedInCompany.getId(), price);
	}
	
	/**
	 * method to get the available coupons from the logged in company user
	 * @return - List of coupons objects
	 */

	public List getCouponsByDate()	{
		
//		Date today = Calendar.getInstance().getTime();LocalDate.parse("2022-03-11"), 
		return companyRepo.getCouponsByDate(loggedInCompany.getId(), LocalDate.now());
		
	}
		
	public Company getLastCompany(){
		return loggedInCompany;
		
	}

}
