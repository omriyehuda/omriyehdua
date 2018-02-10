package com.example.demo.DBDAO;

import java.util.ArrayList;
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
	
	
	@Autowired
	private CompanyRepo companyRepo;
	
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
		
		Company tempCompany = companyRepo.findCompanyById(c.getId());
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
		return null;
	}

	@Override
	public boolean login(String company_name, String password) {
		
		if (companyRepo.findCompanyByNameAndPassword(company_name, password)!=null ){
			return true ;
		}
		
		return false;
	}

	
	public List getCouponsByType(Company company,CouponType type){
		
		int id = company.getId();
		return companyRepo.getCouponsByType(id, type);
		
	}
	

		
	public List getCouponsByPrice (Company company , double price )	{
		
		int id = company.getId();
		return companyRepo.getAllCouponsByPrice(id, price);
	}
	
	

	public List getCouponsByDate(Company company,Date date)	{
		
		int id = company.getId();
		return companyRepo.getCouponsByDate(id, date);
		
	}
		

}
