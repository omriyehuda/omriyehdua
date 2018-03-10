package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.CompanyRepo;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.CouponRepo;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CustomerDoesntExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.UserNameNotMatchException;
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
	@Autowired
	TransactionsDBDAO transactionsDbdao;

	SimpleDateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
	// c.setEndDate(newDate.parse("9/02/20019"));
	// Date today = Calendar.getInstance().getTime();

	// adminFacade tests
	@DirtiesContext
	@Test(expected = PasswordNotCorrectException.class)
	public void loginPasswordNotCorrectException() {
		adminFacade.login("omri", "0542515o", ClientType.Admin);
	}
	@DirtiesContext
	@Test(expected = UserNameNotMatchException.class)
	public void loginUserNameNotMatchException() {
		adminFacade.login(" ", "0542515", ClientType.Admin);
	}
	@DirtiesContext
	@Test
	public void login() {
		adminFacade.login("omri", "0542515", ClientType.Admin);
	}
	@DirtiesContext
	@Test
	public void createCompany() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);
		Company dbCompany = new Company();
		dbCompany = companyRepo.findCompanyByCompanyName(company1.getCompanyName());
		Assert.assertEquals(dbCompany, company1);
	}
	@DirtiesContext
	@Test
	public void removeCompany() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("nike", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);
		Company dbCompany = new Company();
		dbCompany = companyRepo.findCompanyByCompanyName(company1.getCompanyName());
		Assert.assertEquals(dbCompany, company1);

		adminFacade.removeCompany(company1);
	}
	@DirtiesContext
	@Test(expected = CompanyDoesntExistExeption.class)
	public void removeCompanyExeption() {
		Company doesntExistCompany = new Company();
		adminFacade.removeCompany(doesntExistCompany);
	}
	@DirtiesContext
	@Test
	public void updateCompany() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("shmulik vebanav", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);
		Company dbCompany = new Company();
		dbCompany = companyRepo.findCompanyByCompanyName(company1.getCompanyName());
		Assert.assertEquals(dbCompany, company1);

		company1.setEmail("@omri");
		company1.setPassword("1111");
		adminFacade.updateCompany(company1);
		Company testCompany = companyRepo.findCompanyById(company1.getId());
		Assert.assertEquals(testCompany, company1);

	}
	@DirtiesContext
	@Test(expected = CompanyDoesntExistExeption.class)
	public void updateCompanyExeption() {
		Company doesntExistCompany = new Company();
		adminFacade.updateCompany(doesntExistCompany);
	}
	@DirtiesContext
	@Test
	public void getCompany() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("blade", "1234", "blade@gmail.com");
		adminFacade.createCompany(company1);
		Company testCompany = new Company();
		testCompany = adminFacade.getCompany(company1.getId());
		Assert.assertEquals(testCompany, company1);

	}
	@DirtiesContext
	@Test(expected = CompanyDoesntExistExeption.class)
	public void getCompanyExeption() {
		Company doesntExistCompany = new Company();
		adminFacade.getCompany(doesntExistCompany.getId());
	}

	@DirtiesContext
	@Test
	public void getAllCompanies() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Collection<Company> testListOfCompanies = new ArrayList();
		Company c;
		for (int i = 1; i < 5; i++) {
			c = new Company("slingshot" + i, "1234", "slingshot@gmail.com");

			adminFacade.createCompany(c);
			testListOfCompanies.add(c);
		}
		Collection<Company> testListOfCompanies2 = new ArrayList();
		testListOfCompanies2 = adminFacade.getAllCompanies();
		Assert.assertEquals(testListOfCompanies2, testListOfCompanies);
	}

	@DirtiesContext
	@Test
	public void createCustomer() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);
	}

	@DirtiesContext
	@Test(expected = NameAllreadyExistException.class)
	public void createCustomerExeption1() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer2 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer2);

	}
	
	@DirtiesContext
	@Test
	public void updateCustomer(){
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);
		
		Coupon c;
		Collection <Coupon> tempListOfCoupons = new ArrayList();
		for (int i = 0 ; i < 5 ; i++){
			c = new Coupon ();
			c.setTitle("testCoupon"+i);
			tempListOfCoupons.add(c);
			couponRepo.save(c);
		}
		customer1.setPassword("11111");
		customer1.setCoupons(tempListOfCoupons);
		customer1.setCustomerName("SSS");
		
		adminFacade.updateCustomer(customer1); 
		
		Customer tempCustomerFromDb2 = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);
		
	}
	
	@DirtiesContext
	@Test(expected = CustomerDoesntExistExeption.class)
	public void updateCustomerExeption(){
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		
		adminFacade.updateCustomer(customer1 );
	}
	
	@DirtiesContext
	@Test
	public void removeCustomer(){
		
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);
		
		adminFacade.removeCustomer(customer1);
		
	}
	
	@DirtiesContext
	@Test(expected = CustomerDoesntExistExeption.class)
	public void removeCustomerExeption(){
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		
		adminFacade.removeCustomer(customer1);
		
	}
	
	@DirtiesContext
	@Test(expected = CustomerDoesntExistExeption.class)
	public void getCustomerExeption(){
		
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		
		adminFacade.getCustomer(customer1.getId());
	}
	
	@DirtiesContext
	@Test
	public void getCustomer (){
		
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = adminFacade.getCustomer(customer1.getId());
		Assert.assertEquals(tempCustomerFromDb, customer1);
	}
	
	@DirtiesContext
	@Test
	public void getAllCustomer(){
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer c ;
		Collection <Customer> tempListOfCustomers = new ArrayList();
		
		for (int i = 0 ; i<5 ; i ++ ){
			c = new Customer ("omri" + i , "1" + i);
			adminFacade.createCustomer(c);
			tempListOfCustomers.add(c);
		}
		Assert.assertEquals(tempListOfCustomers, adminFacade.getAllCustomers());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}