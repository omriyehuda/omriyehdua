package com.example.demo.DAO;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
@Component
public interface CompanyDAO {
	
	void createCompany (Company c);
	void removeCompany ( Company c);
	void updateCompany (Company c);
	Company getCompany(int id);// check that the id is Company id
	Collection getAllCompanies();
	Collection getCoupons();
	boolean login (String company_name , String password );
	

}
