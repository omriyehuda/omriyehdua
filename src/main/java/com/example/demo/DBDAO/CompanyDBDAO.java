package com.example.demo.DBDAO;

import java.text.SimpleDateFormat;
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
@Component
public class CompanyDBDAO implements CompanyDAO{
	
	Company loggedInCompany;
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;
	
	
	ArrayList<Company> listOfCompanies =new ArrayList<Company>();
	
	
	public CompanyDBDAO(){
		
	}
	
	@Override
	public void createCompany(Company c) {
		companyRepo.save(c);
		
	}
	
	public Company getCompanyByName (String name){
	
		return companyRepo.findCompanyByCompanyName(name);

	}
	

	@Override
	public void removeCompany(Company c) {
	
		companyRepo.delete(c);
		
	}

	@Override
	public void updateCompany(Company c) throws CompanyDoesntExistExeption{
		
		Company tempCompany = companyRepo.findCompanyByCompanyName(c.getCompanyName());
		tempCompany.setCoupons(c.getCoupons());
		tempCompany.setEmail(c.getEmail());
		tempCompany.setPassword(c.getPassword());
		
		companyRepo.save(tempCompany);
	}

	@Override
	public Company getCompany(int id) {
		return companyRepo.findCompanyById(id);
	}

	@Override
	public Collection<Company> getAllCompanies() {
		
		return (ArrayList<Company>) companyRepo.findAll();
	}

	@Override
	public Collection getCoupons() {
		
		return loggedInCompany.getCoupons();
	}

	@Override
	public boolean login(String company_name, String password) {
		
		if (companyRepo.findCompanyByNameAndPassword(company_name, password)!=null ){
			loggedInCompany = companyRepo.findCompanyByNameAndPassword(company_name,password);
			
			return true ;
		}
		
		return false;
	}

	
	public List getCouponsByType(CouponType type){
		
		
		return companyRepo.getCouponsByType(loggedInCompany.getId(), type);
		
	}
	

		
	public List getCouponsByPrice ( double price )	{
		
	
		return companyRepo.getAllCouponsByPrice(loggedInCompany.getId(), price);
	}
	
	

	public List getCouponsByDate()	{
		
		Date today = Calendar.getInstance().getTime();
		return companyRepo.getCouponsByDate(loggedInCompany.getId(), today);
		
	}
		

}
