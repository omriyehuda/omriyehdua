package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.DBDAO.TransactionsDBDAO;
import com.example.demo.Entities.Company;
import com.example.demo.Entities.Coupon;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.EnumFacade;
import com.example.demo.Exception.CompanyDoesntExistExeption;
import com.example.demo.Exception.CustomerAllReadyExistException;
import com.example.demo.Exception.CustomerDoesntExistExeption;
import com.example.demo.Exception.NameAllreadyExistException;
import com.example.demo.Exception.PasswordNotCorrectException;
import com.example.demo.Exception.UserNameNotMatchException;

@Component
public class AdminFacade implements CouponClientFacade {

	@Autowired
	private CompanyDBDAO companyDbdao;
	@Autowired
	private CustomerDBDAO customerDbdao;
	@Autowired
	private CouponDBDAO couponDbdao;
	@Autowired
	private TransactionsDBDAO transactionsDbdao;

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (name != ("omri")) {
			transactionsDbdao.writeToTable("login", false, EnumFacade.AdminFacade);
			throw new UserNameNotMatchException("the user name isnt correct");
		}
		if (password != "0542515") {
			transactionsDbdao.writeToTable("login", false, EnumFacade.AdminFacade);
			throw new PasswordNotCorrectException("password doesnt correct");
		}

		if (name.equals("omri") && password.equals("0542515") && clientType.equals(clientType.Admin)) {
			transactionsDbdao.writeToTable("login", true, EnumFacade.AdminFacade);
			return this;
		}
		return null;
	}

	public AdminFacade() {

	}

	/**
	 * 
	 * @param company
	 * @throws NameAllreadyExistException
	 */
	public void createCompany(Company company) throws NameAllreadyExistException {

		if (companyDbdao.getCompanyByName(company.getCompanyName()) != null) {
			transactionsDbdao.writeToTableCompany("createCompany", false, EnumFacade.AdminFacade, company.toString());
			throw new NameAllreadyExistException("there is a company with that name");
		}

		companyDbdao.createCompany(company);
		System.out.println(company);
		System.out.println(transactionsDbdao);
		transactionsDbdao.writeToTableCompany("createCompany", true, EnumFacade.AdminFacade, company.toString());
	}

	public void removeCompany(Company company) throws CompanyDoesntExistExeption {

		if (companyDbdao.getCompany(company.getId()) == null) {

			throw new CompanyDoesntExistExeption("company doesnt exist");

		}
		transactionsDbdao.writeToTableCompany("removeCompany", true, EnumFacade.AdminFacade, company.toString());
		companyDbdao.removeCompany(company);
	}

	public void updateCompany(Company company) throws CompanyDoesntExistExeption {

		if (companyDbdao.getCompany(company.getId()) != null) {
			transactionsDbdao.writeToTableCompany("updateCompany", true, EnumFacade.AdminFacade, company.toString());
			companyDbdao.updateCompany(company);

		} else
			throw new CompanyDoesntExistExeption("company doenst exist");
	}

	public Company getCompany(int id) throws CompanyDoesntExistExeption {

		if (companyDbdao.getCompany(id) == null) {
			throw new CompanyDoesntExistExeption("company doesnt exist in db");
		}
		transactionsDbdao.writeToTableCompany("getCompany", true, EnumFacade.AdminFacade,
				companyDbdao.getCompany(id).toString());
		return companyDbdao.getCompany(id);
	}

	public Collection getAllCompanies() {
		Collection<Company> AllCompaniesList = new ArrayList<Company>();
		AllCompaniesList = companyDbdao.getAllCompanies();
		transactionsDbdao.writeToTable("getAllCompany", true, EnumFacade.AdminFacade);
		return AllCompaniesList;

	}

	public void createCustomer(Customer customer) throws NameAllreadyExistException {

		if (customerDbdao.getCustomerByName(customer.getCustomerName()) == null) {
			transactionsDbdao.writeToTableCustomer("createCustomer", true, EnumFacade.AdminFacade, customer.toString());
			customerDbdao.createCustomer(customer);
		}

		else
			throw new NameAllreadyExistException("there is a customer by this name all ready, please chage");
		transactionsDbdao.writeToTable("NameAllreadyExistException", false, EnumFacade.AdminFacade);
	}

	public void updateCustomer(Customer customer) throws CustomerDoesntExistExeption {

		if (customerDbdao.getCustomer(customer.getId())== null) {
			transactionsDbdao.writeToTable("CustomerDoesntExistExeption", false, EnumFacade.AdminFacade);
			throw new CustomerDoesntExistExeption("the customer doesnt exist");
		}

		customerDbdao.updateCustomer(customer);
		transactionsDbdao.writeToTableCustomer("updateCustomer", true, EnumFacade.AdminFacade, customer.toString());
	}

	public void removeCustomer(Customer customer) throws CustomerDoesntExistExeption {

		if (customerDbdao.getCustomer(customer.getId()) != null) {
	
			transactionsDbdao.writeToTableCustomer("removeCustomer", true, EnumFacade.AdminFacade, customer.toString());
			customerDbdao.removeCustomer(customer);
		}

		else
			throw new CustomerDoesntExistExeption("the customer doesnt exist");
		transactionsDbdao.writeToTable("CustomerDoesntExistExeption", false, EnumFacade.AdminFacade);

	}

	public Customer getCustomer(int id) throws CustomerDoesntExistExeption{
		if (customerDbdao.getCustomer(id)!=null){
			transactionsDbdao.writeToTableCustomer("getCustomer", true, EnumFacade.AdminFacade,
					customerDbdao.getCustomer(id).toString());
			return customerDbdao.getCustomer(id);
		}
		
		throw new CustomerDoesntExistExeption("the customer doesnt exist");
	}

	public Collection getAllCustomers() {

		Collection<Customer> listOfCustomers = new ArrayList<Customer>();
		listOfCustomers = customerDbdao.getAllCustomers();
		transactionsDbdao.writeToTable("getAllCustomers", true, EnumFacade.AdminFacade);
		return listOfCustomers;

	}

	

}