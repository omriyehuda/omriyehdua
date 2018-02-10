package com.example.demo;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.ClientType;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CustomerFacade;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponProjectApplicationTests {

	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	CustomerRepo customersRepo;
	@Autowired
	CompanyDBDAO CompanyDbdao; 
	@Autowired
	AdminFacade adminFacade;
	@Autowired
	CustomerDBDAO customerDbdao;
	@Autowired
	CompanyFacade couponFacade;
	@Autowired
	CouponDBDAO couponDbdao;
	@Autowired
	CompanyFacade companyFacade;	
	@Autowired
	CustomerFacade customerFacade;
	
	SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
	//c.setEndDate(newDate.parse("9/02/20019"));
	
	@Test
	public void netTest() {
		
		

		System.out.println(CompanyDbdao.login("omri's company","admin"));
		
		
	}
		

}