package com.example.demo;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.example.demo.Entities.CouponType;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.CustomerRepo;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CouponAllReadyExistException;
import com.example.demo.Exception.CouponDoesntAvailableExeption;
import com.example.demo.Exception.CouponDoesntExistExeption;
import com.example.demo.Exception.CouponTitleAllreadyExistException;
import com.example.demo.Exception.CustomerDoesntExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.PriceCantBeMinusException;
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
	public void adminLogin() {
		assertNotNull(adminFacade.login("omri", "0542515", ClientType.Admin));

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
	@Test(expected = NameAllreadyExistException.class)
	public void createCompanyExeption() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		adminFacade.createCompany(company1);
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
	public void updateCustomer() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);

		Coupon c;
		Collection<Coupon> tempListOfCoupons = new ArrayList();
		for (int i = 0; i < 5; i++) {
			c = new Coupon();
			c.setTitle("testCoupon" + i);
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
	public void updateCustomerExeption() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");

		adminFacade.updateCustomer(customer1);
	}

	@DirtiesContext
	@Test
	public void removeCustomer() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = customersRepo.findCustomerByCustomerName(customer1.getCustomerName());
		Assert.assertEquals(tempCustomerFromDb, customer1);

		adminFacade.removeCustomer(customer1);

	}

	@DirtiesContext
	@Test(expected = CustomerDoesntExistExeption.class)
	public void removeCustomerExeption() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");

		adminFacade.removeCustomer(customer1);

	}

	@DirtiesContext
	@Test(expected = CustomerDoesntExistExeption.class)
	public void getCustomerExeption() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");

		adminFacade.getCustomer(customer1.getId());
	}

	@DirtiesContext
	@Test
	public void getCustomer() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer1 = new Customer("omri", "0542515");
		adminFacade.createCustomer(customer1);
		Customer tempCustomerFromDb = adminFacade.getCustomer(customer1.getId());
		Assert.assertEquals(tempCustomerFromDb, customer1);
	}

	@DirtiesContext
	@Test
	public void getAllCustomer() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer c;
		Collection<Customer> tempListOfCustomers = new ArrayList();

		for (int i = 0; i < 5; i++) {
			c = new Customer("omri" + i, "1" + i);
			adminFacade.createCustomer(c);
			tempListOfCustomers.add(c);
		}
		Assert.assertEquals(tempListOfCustomers, adminFacade.getAllCustomers());

	}

	// -----------------------------CompanyFacade
	// tests---------------------------

	@DirtiesContext
	@Test
	public void loginCompanyFacade() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
	}

	@DirtiesContext
	@Test
	public void creatCoupon() throws ParseException {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponAllReadyExistException.class)
	public void createCouponExeption1() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		companyFacade.createCoupon(newCoupon);

		companyFacade.createCoupon(newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponTitleAllreadyExistException.class)
	public void createCouponExeption2() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		companyFacade.createCoupon(newCoupon);

		Coupon newCoupon2 = new Coupon("board", LocalDate.parse("2018-03-09"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Sport, "", 500, "no massage");

		companyFacade.createCoupon(newCoupon2);

	}

	@DirtiesContext
	@Test
	public void removeCoupon() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

		companyFacade.removeCoupon(newCoupon);

	}

	@DirtiesContext
	@Test(expected = CouponDoesntExistExeption.class)
	public void removeCouponException() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.removeCoupon(newCoupon);

	}

	@DirtiesContext
	@Test
	public void updateCoupon() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(newCoupon);
		Coupon testCoupon = new Coupon();
		testCoupon = couponRepo.findCouponByTitle(newCoupon.getTitle());

		Assert.assertEquals(testCoupon, newCoupon);

		newCoupon.setImage("buifs");
		newCoupon.setStartDate(LocalDate.parse("2228-03-10"));
		newCoupon.setTitle("trapes");
		newCoupon.setPrice(400);

		companyFacade.updateCoupon(newCoupon);

		Coupon testCoupon2 = couponRepo.findCouponById(newCoupon.getId());
		Assert.assertEquals(testCoupon2, newCoupon);
	}

	@DirtiesContext
	@Test(expected = CouponDoesntExistExeption.class)
	public void updateCouponException() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Coupon newCoupon = new Coupon("board", LocalDate.parse("2018-03-10"), LocalDate.parse("2019-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		newCoupon.setImage("buifs");
		newCoupon.setStartDate(LocalDate.parse("2228-03-10"));
		newCoupon.setTitle("trapes");
		newCoupon.setPrice(400);

		companyFacade.updateCoupon(newCoupon);

	}

	@DirtiesContext
	@Test
	public void getCompany2() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Company company2 = new Company("f-one", "1234", "f-one@gmail.com");
		adminFacade.createCompany(company2);

		Assert.assertEquals(companyFacade.getCompany(company2.getId()), company2);

	}

	@DirtiesContext
	@Test(expected = CompanyDoesntExistExeption.class)
	public void getCompanyException() {
		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		Company company2 = new Company("f-one", "1234", "f-one@gmail.com");

		companyFacade.getCompany(company2.getId());

	}

	@DirtiesContext
	@Test
	public void getAllCoupons() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Collection<Coupon> tempListOfCoupons = new ArrayList();
		Coupon c;

		for (int i = 0; i < 5; i++) {

			c = new Coupon("boost" + i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000, "no massage");
			tempListOfCoupons.add(c);
			companyFacade.createCoupon(c);

		}
		Assert.assertEquals(tempListOfCoupons, companyFacade.getAllCoupons());

	}

	@DirtiesContext
	@Test(expected = CouponDoesntExistExeption.class)
	public void getAllCouponsException() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));

		companyFacade.getAllCoupons();
	}

	@DirtiesContext
	@Test
	public void getCouponsByType() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c = new Coupon("boost", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c1 = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Electronic, "freeStyle", 1000, "no massage");
		Coupon c2 = new Coupon("boost2", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Food, "freeStyle", 1000, "no massage");
		Coupon c3 = new Coupon("boost3", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Fun, "freeStyle", 1000, "no massage");
		Coupon c4 = new Coupon("boost4", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Fun, "freeStyle", 1000, "no massage");
		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		Collection<Coupon> tempListOfCoupon = new ArrayList();
		tempListOfCoupon = companyFacade.getCouponsByType(CouponType.Fun);

		Assert.assertEquals(tempListOfCoupon, companyFacade.getCouponsByType(CouponType.Fun));

	}

	@DirtiesContext
	@Test(expected = CouponDoesntExistExeption.class)
	public void getCouponsByTypeException() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c = new Coupon("boost", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c1 = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Electronic, "freeStyle", 1000, "no massage");
		Coupon c2 = new Coupon("boost2", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Food, "freeStyle", 1000, "no massage");
		Coupon c3 = new Coupon("boost3", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Fun, "freeStyle", 1000, "no massage");
		Coupon c4 = new Coupon("boost4", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Fun, "freeStyle", 1000, "no massage");
		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		System.out.println(companyFacade.getCouponsByType(CouponType.Movies));

	}

	@DirtiesContext
	@Test
	public void getCouponsByprice() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c;
		Collection<Coupon> ListOfCouponsByPrice = new ArrayList();
		for (int i = 0; i < 3; i++) {

			c = new Coupon("boost" + i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000 * i, "no massage");

			companyFacade.createCoupon(c);
			ListOfCouponsByPrice.add(c);
		}

		Assert.assertEquals(ListOfCouponsByPrice, companyFacade.getCouponsByPrice(3001));

	}

	@DirtiesContext
	@Test(expected = PriceCantBeMinusException.class)
	public void getCouponsBypriceException1() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c;

		for (int i = 0; i < 3; i++) {

			c = new Coupon("boost" + i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000 * i, "no massage");

			companyFacade.createCoupon(c);

		}

		companyFacade.getCouponsByPrice(-10);

	}

	@DirtiesContext
	@Test(expected = CouponDoesntExistExeption.class)
	public void getCouponsBypriceException2() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c;

		for (int i = 3; i < 6; i++) {

			c = new Coupon("boost" + i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000 * i, "no massage");

			companyFacade.createCoupon(c);
		}
		companyFacade.getCouponsByPrice(100);

	}

	@DirtiesContext
	@Test
	public void getCouponsByDate() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Collection<Coupon> couponListByNow = new ArrayList();

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c1 = new Coupon("boost2", LocalDate.parse("2018-03-11"), LocalDate.parse("2021-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c2 = new Coupon("boost3", LocalDate.parse("2018-03-11"), LocalDate.parse("2022-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c3 = new Coupon("boost4", LocalDate.parse("2018-03-11"), LocalDate.parse("2023-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c4 = new Coupon("boost5", LocalDate.parse("2018-03-11"), LocalDate.parse("2024-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		couponListByNow.add(c);
		couponListByNow.add(c1);
		couponListByNow.add(c2);
		couponListByNow.add(c3);
		couponListByNow.add(c4);

		Assert.assertEquals(couponListByNow, companyFacade.getCouponsByDate());

	}

	@DirtiesContext
	@Test(expected = CouponDoesntAvailableExeption.class)
	public void getCouponsByDateException() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2010-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c1 = new Coupon("boost2", LocalDate.parse("2018-03-11"), LocalDate.parse("2011-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c2 = new Coupon("boost3", LocalDate.parse("2018-03-11"), LocalDate.parse("2012-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c3 = new Coupon("boost4", LocalDate.parse("2018-03-11"), LocalDate.parse("2013-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");
		Coupon c4 = new Coupon("boost5", LocalDate.parse("2018-03-11"), LocalDate.parse("2014-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(c);
		companyFacade.createCoupon(c1);
		companyFacade.createCoupon(c2);
		companyFacade.createCoupon(c3);
		companyFacade.createCoupon(c4);

		companyFacade.getCouponsByDate();

	}

	// ---------------------------------------------- Customer Facade tests
	// -------------------------------------

	@DirtiesContext
	@Test
	public void loginCustomerFacade() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);
		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));
	}

	@DirtiesContext
	@Test
	public void purchesCoupon() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(c);

		customerFacade.purchaseCoupon(c);
		
		Assert.assertTrue(customerFacade.getAllPurchaseCoupons().contains(c));		
}

	@DirtiesContext
	@Test(expected = CouponDoesntAvailableExeption.class)
	public void purchesCouponException1() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2010-03-11"), 10,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(c);
		customerFacade.purchaseCoupon(c);

	}

	
	
	@DirtiesContext
	@Test (expected = CouponDoesntExistExeption.class)
	public void purchesCouponException2() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), -1,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		companyFacade.createCoupon(c);
		customerFacade.purchaseCoupon(c);
	}
	
	@DirtiesContext
	@Test (expected = CouponDoesntExistExeption.class)
	public void purchesCouponException3() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Coupon c = new Coupon("boost1", LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 100,
				CouponType.Sport, "freeStyle", 1000, "no massage");

		
		customerFacade.purchaseCoupon(c);
	}
	
	@DirtiesContext
	@Test
	public void getAllPurchesCoupon() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("boost"+i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCoupons());
		
	}
	
	@DirtiesContext
	@Test
	public void getAllPurchesCouponByType() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("boost"+i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCouponsByType(CouponType.Sport));
		
	}
	
	
	@DirtiesContext
	@Test
	public void getAllPurchesCouponByPrice() {

		this.adminFacade.login("omri", "0542515", ClientType.Admin);

		Company company1 = new Company("rrd", "1234", "rrd@gmail.com");
		adminFacade.createCompany(company1);

		assertNotNull(companyFacade.login("rrd", "1234", ClientType.Company));
		companyFacade.login("rrd", "1234", ClientType.Company);

		Customer customer = new Customer("omri", "1234");
		adminFacade.createCustomer(customer);

		assertNotNull(customerFacade.login("omri", "1234", ClientType.Customer));

		customerFacade.login("omri", "1234", ClientType.Customer);

		Collection <Coupon> listOfTempCoupons = new ArrayList();
		Coupon c;
		for(int i = 0 ; i < 5 ; i ++){
			
			c = new Coupon("boost"+i, LocalDate.parse("2018-03-11"), LocalDate.parse("2020-03-11"), 10,
					CouponType.Sport, "freeStyle", 1000, "no massage");
			
			companyFacade.createCoupon(c);
			listOfTempCoupons.add(c);
			customerFacade.purchaseCoupon(c);
		}
		
		Assert.assertEquals(listOfTempCoupons, customerFacade.getAllPurchaseCouponsByPrice(1000));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
